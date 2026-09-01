package ex15.step10;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

// STEP 10. 앞 단계의 설계를 조합하고 HTML/PUSH 확장으로 OCP를 확인한다.
enum StatementFormat {
    PDF, CSV, HTML
}

enum DeliveryChannel {
    EMAIL, SMS, FAX, PUSH
}

class User {
    String id;
    boolean login;
    boolean statementManager;
    String allowedAccountNo;

    User(String id, boolean login, boolean statementManager, String allowedAccountNo) {
        this.id = id;
        this.login = login;
        this.statementManager = statementManager;
        this.allowedAccountNo = allowedAccountNo;
    }
}

class StatementRequest {
    String accountNo;
    String period;
    StatementFormat format;
    DeliveryChannel channel;
    String receiver;

    StatementRequest(
            String accountNo,
            String period,
            StatementFormat format,
            DeliveryChannel channel,
            String receiver
    ) {
        this.accountNo = accountNo;
        this.period = period;
        this.format = format;
        this.channel = channel;
        this.receiver = receiver;
    }
}

// ---------- 책임 연쇄: 요청 검증 ----------
abstract class RequestValidator {
    private RequestValidator next;

    RequestValidator setNext(RequestValidator next) {
        this.next = next;
        return next;
    }

    public final boolean 검증한다(User user, StatementRequest request) {
        if (!현재검증을통과한다(user, request)) {
            return false;
        }
        return next == null || next.검증한다(user, request);
    }

    protected abstract boolean 현재검증을통과한다(User user, StatementRequest request);
}

class LoginValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.login) {
            System.out.println("[중단] 로그인이 필요합니다.");
            return false;
        }
        System.out.println("[검증] 로그인 확인");
        return true;
    }
}

class RoleValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.statementManager) {
            System.out.println("[중단] 거래명세서 발송 권한이 없습니다.");
            return false;
        }
        System.out.println("[검증] 직원 권한 확인");
        return true;
    }
}

class AccountValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.allowedAccountNo.equals(request.accountNo)) {
            System.out.println("[중단] 이 계좌에 접근할 수 없습니다.");
            return false;
        }
        System.out.println("[검증] 계좌 접근 권한 확인");
        return true;
    }
}

// ---------- 프록시: 원장 조회 캐시 ----------
interface TransactionSource {
    String 거래내역조회(String accountNo, String period);
}

class CoreBankingTransactionSource implements TransactionSource {
    @Override
    public String 거래내역조회(String accountNo, String period) {
        System.out.println("[원장 시스템] " + accountNo + " / " + period + " 거래내역 조회");
        return "입금 100,000원 / 출금 30,000원";
    }
}

class CachedTransactionSourceProxy implements TransactionSource {
    private final TransactionSource target;
    private final Map<String, String> cache = new HashMap<>();

    CachedTransactionSourceProxy(TransactionSource target) {
        this.target = target;
    }

    @Override
    public String 거래내역조회(String accountNo, String period) {
        String key = accountNo + ":" + period;

        if (cache.containsKey(key)) {
            System.out.println("[캐시 HIT] 저장된 거래내역을 반환합니다.");
            return cache.get(key);
        }

        System.out.println("[캐시 MISS] 실제 원장 시스템을 조회합니다.");
        String transactions = target.거래내역조회(accountNo, period);
        cache.put(key, transactions);
        return transactions;
    }
}

// ---------- 템플릿 메서드: 문서 생성 순서 ----------
abstract class StatementGenerator {

    public final String 생성한다(String transactions) {
        준비한다();
        머리말을작성한다();
        거래내역을작성한다(transactions);
        꼬리말을작성한다();
        return 완성된문서();
    }

    private void 준비한다() {
        System.out.println("[문서] 거래명세서 생성을 준비합니다.");
    }

    protected abstract void 머리말을작성한다();

    protected abstract void 거래내역을작성한다(String transactions);

    protected abstract void 꼬리말을작성한다();

    protected abstract String 완성된문서();
}

class PdfStatementGenerator extends StatementGenerator {
    @Override
    protected void 머리말을작성한다() {
        System.out.println("[PDF] 은행 로고와 제목 작성");
    }

    @Override
    protected void 거래내역을작성한다(String transactions) {
        System.out.println("[PDF] 표로 작성: " + transactions);
    }

    @Override
    protected void 꼬리말을작성한다() {
        System.out.println("[PDF] 안내 문구 작성");
    }

    @Override
    protected String 완성된문서() {
        return "PDF 거래명세서";
    }
}

class CsvStatementGenerator extends StatementGenerator {
    @Override
    protected void 머리말을작성한다() {
        System.out.println("[CSV] 열 이름 작성");
    }

    @Override
    protected void 거래내역을작성한다(String transactions) {
        System.out.println("[CSV] 행으로 작성: " + transactions);
    }

    @Override
    protected void 꼬리말을작성한다() {
        System.out.println("[CSV] 마지막 줄 확인");
    }

    @Override
    protected String 완성된문서() {
        return "CSV 거래명세서";
    }
}

// 새 문서 형식은 기존 생성기와 서비스를 수정하지 않고 클래스로 추가한다.
class HtmlStatementGenerator extends StatementGenerator {
    @Override
    protected void 머리말을작성한다() {
        System.out.println("[HTML] 제목 태그 작성");
    }

    @Override
    protected void 거래내역을작성한다(String transactions) {
        System.out.println("[HTML] 테이블로 작성: " + transactions);
    }

    @Override
    protected void 꼬리말을작성한다() {
        System.out.println("[HTML] 닫는 태그 작성");
    }

    @Override
    protected String 완성된문서() {
        return "HTML 거래명세서";
    }
}

// ---------- 전략: 발송 방법 ----------
interface StatementSender {
    void 발송한다(String receiver, String document);
}

class EmailStatementSender implements StatementSender {
    @Override
    public void 발송한다(String receiver, String document) {
        System.out.println("[이메일] " + receiver + "에게 발송: " + document);
    }
}

class SmsStatementSender implements StatementSender {
    @Override
    public void 발송한다(String receiver, String document) {
        System.out.println("[문자] " + receiver + "에게 다운로드 링크 발송: " + document);
    }
}

class PushStatementSender implements StatementSender {
    @Override
    public void 발송한다(String receiver, String document) {
        System.out.println("[푸시] " + receiver + "에게 앱 알림 발송: " + document);
    }
}

class LegacyFaxClient {
    void transmit(String phoneNumber, String payload) {
        System.out.println("[기존 팩스] " + phoneNumber + "로 전송: " + payload);
    }
}

// ---------- 어댑터: 기존 팩스 API 변환 ----------
class FaxStatementSenderAdapter implements StatementSender {
    private final LegacyFaxClient faxClient;

    FaxStatementSenderAdapter(LegacyFaxClient faxClient) {
        this.faxClient = faxClient;
    }

    @Override
    public void 발송한다(String receiver, String document) {
        faxClient.transmit(receiver, document);
    }
}

// ---------- 팩토리: 형식과 채널에 맞는 구현체 생성 ----------
class GeneratorFactory {
    private final Map<StatementFormat, Supplier<StatementGenerator>> creators = new HashMap<>();

    void 등록한다(StatementFormat format, Supplier<StatementGenerator> creator) {
        creators.put(format, creator);
    }

    StatementGenerator 생성한다(StatementFormat format) {
        Supplier<StatementGenerator> creator = creators.get(format);
        if (creator == null) {
            throw new IllegalArgumentException("지원하지 않는 문서 형식: " + format);
        }
        return creator.get();
    }
}

class SenderFactory {
    private final Map<DeliveryChannel, Supplier<StatementSender>> creators = new HashMap<>();

    void 등록한다(DeliveryChannel channel, Supplier<StatementSender> creator) {
        creators.put(channel, creator);
    }

    StatementSender 생성한다(DeliveryChannel channel) {
        Supplier<StatementSender> creator = creators.get(channel);
        if (creator == null) {
            throw new IllegalArgumentException("지원하지 않는 발송 채널: " + channel);
        }
        return creator.get();
    }
}

// ---------- 옵저버: 발송 완료 후속 작업 ----------
class StatementSentEvent {
    String userId;
    String receiver;
    String document;

    StatementSentEvent(String userId, String receiver, String document) {
        this.userId = userId;
        this.receiver = receiver;
        this.document = document;
    }
}

interface StatementSentObserver {
    void 발송완료(StatementSentEvent event);
}

class AuditLogObserver implements StatementSentObserver {
    @Override
    public void 발송완료(StatementSentEvent event) {
        System.out.println("[감사] " + event.userId + "의 발송 이력을 기록합니다.");
    }
}

class CustomerNotificationObserver implements StatementSentObserver {
    @Override
    public void 발송완료(StatementSentEvent event) {
        System.out.println("[알림] " + event.receiver + "에게 발송 완료를 안내합니다.");
    }
}

class StatementEventPublisher {
    private final List<StatementSentObserver> observers = new ArrayList<>();

    void 구독한다(StatementSentObserver observer) {
        observers.add(observer);
    }

    void 발행한다(StatementSentEvent event) {
        for (StatementSentObserver observer : observers) {
            observer.발송완료(event);
        }
    }
}

// ---------- 핵심 서비스: 추상화된 협력 객체만 사용 ----------
interface StatementUseCase {
    void 명세서를발송한다(User user, StatementRequest request);
}

class StatementService implements StatementUseCase {
    private final RequestValidator validator;
    private final TransactionSource transactionSource;
    private final GeneratorFactory generatorFactory;
    private final SenderFactory senderFactory;
    private final StatementEventPublisher publisher;

    StatementService(
            RequestValidator validator,
            TransactionSource transactionSource,
            GeneratorFactory generatorFactory,
            SenderFactory senderFactory,
            StatementEventPublisher publisher
    ) {
        this.validator = validator;
        this.transactionSource = transactionSource;
        this.generatorFactory = generatorFactory;
        this.senderFactory = senderFactory;
        this.publisher = publisher;
    }

    @Override
    public void 명세서를발송한다(User user, StatementRequest request) {
        System.out.println("[시작] " + request.format + " / " + request.channel);

        if (!validator.검증한다(user, request)) {
            return;
        }

        String transactions = transactionSource.거래내역조회(request.accountNo, request.period);
        StatementGenerator generator = generatorFactory.생성한다(request.format);
        StatementSender sender = senderFactory.생성한다(request.channel);

        String document = generator.생성한다(transactions);
        sender.발송한다(request.receiver, document);
        publisher.발행한다(new StatementSentEvent(user.id, request.receiver, document));
    }
}

// ---------- 커맨드: 발송 요청을 객체로 저장 ----------
interface Command {
    void execute();
}

class SendStatementCommand implements Command {
    private final StatementUseCase statementUseCase;
    private final User user;
    private final StatementRequest request;

    SendStatementCommand(StatementUseCase statementUseCase, User user, StatementRequest request) {
        this.statementUseCase = statementUseCase;
        this.user = user;
        this.request = request;
    }

    @Override
    public void execute() {
        statementUseCase.명세서를발송한다(user, request);
    }
}

class CommandQueue {
    private final Queue<Command> commands = new ArrayDeque<>();

    void 추가한다(Command command) {
        commands.offer(command);
    }

    void 모두실행한다() {
        while (!commands.isEmpty()) {
            commands.poll().execute();
            System.out.println();
        }
    }
}

// ---------- 퍼사드: 클라이언트가 보는 단순한 창구 ----------
class StatementFacade {
    private final StatementUseCase statementUseCase;
    private final CommandQueue commandQueue;

    StatementFacade(StatementUseCase statementUseCase, CommandQueue commandQueue) {
        this.statementUseCase = statementUseCase;
        this.commandQueue = commandQueue;
    }

    void 발송을예약한다(User user, StatementRequest request) {
        commandQueue.추가한다(new SendStatementCommand(statementUseCase, user, request));
        System.out.println("[예약] 거래명세서 발송 요청을 큐에 저장했습니다.");
    }

    void 예약된요청을실행한다() {
        commandQueue.모두실행한다();
    }
}

public class App {
    public static void main(String[] args) {
        // 1. 검증 체인 조립
        RequestValidator validationChain = new LoginValidator();
        validationChain
                .setNext(new RoleValidator())
                .setNext(new AccountValidator());

        // 2. 실제 원장 조회 객체를 캐시 프록시로 감싼다.
        TransactionSource transactionSource = new CachedTransactionSourceProxy(
                new CoreBankingTransactionSource()
        );

        // 3. 팩토리에 생성기를 등록한다. HTML 추가 때문에 서비스는 수정하지 않는다.
        GeneratorFactory generatorFactory = new GeneratorFactory();
        generatorFactory.등록한다(StatementFormat.PDF, PdfStatementGenerator::new);
        generatorFactory.등록한다(StatementFormat.CSV, CsvStatementGenerator::new);
        generatorFactory.등록한다(StatementFormat.HTML, HtmlStatementGenerator::new);

        // 4. 팩토리에 발송 전략을 등록한다. 기존 팩스 시스템은 어댑터로 연결한다.
        LegacyFaxClient legacyFaxClient = new LegacyFaxClient();
        SenderFactory senderFactory = new SenderFactory();
        senderFactory.등록한다(DeliveryChannel.EMAIL, EmailStatementSender::new);
        senderFactory.등록한다(DeliveryChannel.SMS, SmsStatementSender::new);
        senderFactory.등록한다(
                DeliveryChannel.FAX,
                () -> new FaxStatementSenderAdapter(legacyFaxClient)
        );
        senderFactory.등록한다(DeliveryChannel.PUSH, PushStatementSender::new);

        // 5. 발송 완료 뒤 실행할 옵저버를 등록한다.
        StatementEventPublisher publisher = new StatementEventPublisher();
        publisher.구독한다(new AuditLogObserver());
        publisher.구독한다(new CustomerNotificationObserver());

        StatementUseCase statementService = new StatementService(
                validationChain,
                transactionSource,
                generatorFactory,
                senderFactory,
                publisher
        );

        StatementFacade facade = new StatementFacade(statementService, new CommandQueue());
        User user = new User("staff01", true, true, "110-123-456789");

        // 기본 기능
        facade.발송을예약한다(user, new StatementRequest(
                "110-123-456789",
                "2026-08",
                StatementFormat.PDF,
                DeliveryChannel.EMAIL,
                "customer@bank.com"
        ));

        // OCP 확인: HTML 생성기와 PUSH 발송 전략만 추가했다.
        facade.발송을예약한다(user, new StatementRequest(
                "110-123-456789",
                "2026-08",
                StatementFormat.HTML,
                DeliveryChannel.PUSH,
                "customer-device-token"
        ));

        // 어댑터 확인
        facade.발송을예약한다(user, new StatementRequest(
                "110-123-456789",
                "2026-08",
                StatementFormat.CSV,
                DeliveryChannel.FAX,
                "051-000-0000"
        ));

        facade.예약된요청을실행한다();
    }
}
