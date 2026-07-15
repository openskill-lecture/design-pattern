package ex09.reactor.step3;

import java.util.*;

// ── 라이브러리 없이 직접 정의한 Reactive Streams 계약 3종 ──
interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}

interface Subscriber<T> {
    void onSubscribe(Subscription subscription);

    void onNext(T item);

    void onError(Throwable error);

    void onComplete();
}

interface Subscription {
    void request(long n);

    void cancel();
}

// 버퍼(큐)를 들고, 스스로 완결하지 않는 hot Publisher
class BufferPublisher implements Publisher<Integer> {
    private final Queue<Integer> buffer = new LinkedList<>(); // ① 발행할 원소를 버퍼로 보관
    private long demand = 0; // 남은 빚(outstanding demand)
    private Subscriber<? super Integer> sub;
    private boolean done = false;

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        this.sub = s;
        s.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                if (done || n <= 0)
                    return;
                demand += n; // ★ 누적된다 (request는 빚에 더하는 것)
                drain();
            }

            @Override
            public void cancel() {
                done = true;
                sub = null;
            } // 소비자가 끊음
        });
    }

    // ③ 밖에서 원소를 밀어넣는 통로 — 완결형엔 없던 것
    public void emit(int value) {
        if (done)
            return;
        buffer.offer(value);
        drain(); // 빚이 남아있으면 방금 넣은 게 즉시 나감
    }

    // 더 보낼 게 없다고 "명시적으로" 닫을 때만 완결 (버퍼형은 스스로 안 닫음)
    public void complete() {
        if (done)
            return;
        done = true;
        if (sub != null)
            sub.onComplete();
    }

    private void drain() {
        // ② 버퍼에 있고 & 빚이 남은 만큼만 내보낸다. 비어도 onComplete 안 함 → 열린 채 대기
        while (demand > 0 && !buffer.isEmpty() && sub != null) {
            sub.onNext(buffer.poll());
            demand--; // 빚 갚기
        }
    }
}

class MySubscriber implements Subscriber<Integer> {
    private Subscription subscription;

    public Subscription upstream() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        System.out.println("onSubscribe: 계약 수령 (아직 request 안 함 → 0개)");
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("  onNext: " + item);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("onError: " + t);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete!");
    }
}

public class App {
    public static void main(String[] args) {
        BufferPublisher pub = new BufferPublisher();

        MySubscriber sub = new MySubscriber();
        pub.subscribe(sub); // onSubscribe만. 데이터 0개.
        Subscription s = sub.upstream();

        // 명령을 손으로 쳐서 push(emit) ↔ pull(request) 균형을 눈으로 본다.
        // 숫자 → emit : demand 남았으면 즉시 onNext, 없으면 버퍼에 쌓임
        // r N → request: 빚 N 추가. 버퍼에 쌓인 게 있으면 그 자리에서 배출
        // q → complete: ★ 빚 청산만으론 안 끝난다. 이걸 불러야 onComplete
        System.out.println("명령 입력 → 숫자=emit, 'r N'=request(N), 'q'=complete");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
                continue;

            if (line.equals("q")) {
                pub.complete(); // 더 보낼 게 없다고 명시적으로 닫는다 → 여기서야 onComplete
                break;
            } else if (line.startsWith("r ")) {
                long n = Long.parseLong(line.substring(2).trim());
                s.request(n); // 빚 n 추가 → 버퍼에 쌓인 만큼 drain
            } else {
                pub.emit(Integer.parseInt(line)); // demand 남았으면 즉시 나가고, 없으면 버퍼링
            }
        }
        scanner.close();

        // s.cancel(); // ← [참고] 소비자가 중간에 끊고 싶으면 이렇게 호출. 이후 emit/complete는 무시됨.
    }
}