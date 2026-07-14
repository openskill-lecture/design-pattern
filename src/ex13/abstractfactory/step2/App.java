package ex13.abstractfactory.step2;

// 추상 팩토리(어려운 예제): 제품 하나가 아니라 '서로 호환되는 제품군 전체'를 팩토리 하나로 고른다.
// step1(버튼+체크박스 2종)보다 무겁다. 클라우드 배포엔 서버·저장소·큐·DB 네 제품이 함께 필요하고,
// AWS를 고르면 넷 다 AWS, GCP를 고르면 넷 다 GCP로 '가족'이 맞아야 한다.

// --- 추상 제품 4종 : 클라이언트가 아는 건 이 추상 타입뿐 ---
interface Compute {
    void start();
}

interface Storage {
    void upload(String fileName);
}

interface MessageQueue {
    void send(String message);
}

interface Database {
    void connect();
}

// --- AWS 제품군 (EC2 + S3 + SQS + RDS) ---
class AwsEC2 implements Compute {
    public void start() {
        System.out.println("AWS EC2 인스턴스를 시작합니다.");
    }
}

class AwsS3 implements Storage {
    public void upload(String fileName) {
        System.out.println(fileName + "을 AWS S3에 업로드합니다.");
    }
}

class AwsSQS implements MessageQueue {
    public void send(String message) {
        System.out.println("AWS SQS로 메시지 전송: " + message);
    }
}

class AwsRDS implements Database {
    public void connect() {
        System.out.println("AWS RDS에 연결합니다.");
    }
}

// --- GCP 제품군 (Compute Engine + Cloud Storage + Pub/Sub + Cloud SQL) ---
class GcpComputeEngine implements Compute {
    public void start() {
        System.out.println("GCP Compute Engine을 시작합니다.");
    }
}

class GcpCloudStorage implements Storage {
    public void upload(String fileName) {
        System.out.println(fileName + "을 GCP Cloud Storage에 업로드합니다.");
    }
}

class GcpPubSub implements MessageQueue {
    public void send(String message) {
        System.out.println("GCP Pub/Sub으로 메시지 전송: " + message);
    }
}

class GcpCloudSQL implements Database {
    public void connect() {
        System.out.println("GCP Cloud SQL에 연결합니다.");
    }
}

// 추상 팩토리 : 관련 제품 넷을 만드는 공통 통로. '무엇을 만들지'만 약속하고, AWS인지 GCP인지는 모른다.
interface CloudFactory {
    Compute createCompute();
    Storage createStorage();
    MessageQueue createMessageQueue();
    Database createDatabase();
}

// 이 팩토리는 AWS 제품만 만든다 → 네 제품이 절대 다른 클라우드와 섞이지 않는다(가족 일관성).
class AwsCloudFactory implements CloudFactory {
    public Compute createCompute()           { return new AwsEC2(); }
    public Storage createStorage()           { return new AwsS3(); }
    public MessageQueue createMessageQueue() { return new AwsSQS(); }
    public Database createDatabase()         { return new AwsRDS(); }
}

// 이 팩토리는 GCP 제품만 만든다.
class GcpCloudFactory implements CloudFactory {
    public Compute createCompute()           { return new GcpComputeEngine(); }
    public Storage createStorage()           { return new GcpCloudStorage(); }
    public MessageQueue createMessageQueue() { return new GcpPubSub(); }
    public Database createDatabase()         { return new GcpCloudSQL(); }
}

// 클라이언트 : 구체 타입(AwsEC2 등)을 전혀 모른다. CloudFactory에게 시키고 추상 타입으로만 받는다.
class ShoppingMallInfrastructure {
    private final Compute compute;
    private final Storage storage;
    private final MessageQueue messageQueue;
    private final Database database;

    // 팩토리를 주입받아 네 제품을 한 가족으로 조립한다. 어떤 클라우드인지는 팩토리가 결정한다.
    public ShoppingMallInfrastructure(CloudFactory factory) {
        this.compute = factory.createCompute();
        this.storage = factory.createStorage();
        this.messageQueue = factory.createMessageQueue();
        this.database = factory.createDatabase();
    }

    public void deploy() {
        System.out.println("=== 쇼핑몰 인프라 배포 시작 ===");
        compute.start();
        database.connect();
        storage.upload("product-images.zip");
        messageQueue.send("쇼핑몰 배포 완료");
        System.out.println("=== 쇼핑몰 인프라 배포 완료 ===");
    }
}

public class App {
    public static void main(String[] args) {
        // 팩토리 한 줄이 제품군 전체를 결정한다. AWS 팩토리 → EC2+S3+SQS+RDS 통째로.
        CloudFactory factory = new AwsCloudFactory();
        new ShoppingMallInfrastructure(factory).deploy();

        System.out.println("----------------------------");

        // 팩토리 한 줄만 GCP로 바꾸면 네 제품이 통째로 교체된다. ShoppingMallInfrastructure는 그대로.
        factory = new GcpCloudFactory();
        new ShoppingMallInfrastructure(factory).deploy();
    }
}
