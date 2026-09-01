# ex16 - 책임 연쇄, 상태, 커맨드, CQRS

PDF 네 편의 실행 예제를 강의 순서에 맞게 정리한 소스입니다.

| PDF | 실행 클래스 | 내용 |
| --- | --- | --- |
| 01 책임 연쇄 | `ex16.chain.App` | 로그 → 로그인 → 관리자 권한 필터 체인 |
| 02 상태 | `ex16.state.step1.App` | 문자열과 조건문으로 구현한 주문 상태 |
| 02 상태 | `ex16.state.step2.App` | 상태 객체로 구현한 주문 상태 |
| 03 커맨드 | `ex16.command.step1.App` | 작업 종류를 조건문으로 판별하는 큐 |
| 03 커맨드 | `ex16.command.step2.App` | 커맨드 클래스를 보관하는 큐 |
| 03 커맨드 | `ex16.command.step3.App` | 람다와 메서드 참조를 보관하는 큐 |
| 03 커맨드 | `ex16.command.step4.App` | 실행 횟수와 성공 여부를 가진 재시도 커맨드 |
| 04 CQRS | `ex16.cqrs.step1.App` | 하나의 모델로 저장하고 조회하기 |
| 04 CQRS | `ex16.cqrs.step2.App` | 쓰기 모델과 읽기 모델을 나누고 동기화하기 |

프로젝트 루트에서 다음과 같이 실행할 수 있습니다.

```powershell
javac -encoding UTF-8 -d bin src/ex16/chain/App.java
java -cp bin ex16.chain.App
```
