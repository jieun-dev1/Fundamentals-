<aside>

## 순수 Java nio 로 TCP Echo 서버 만들어보기.

[Focus]
- channel과 selector를 사용해서 동시 처리가능하도록 만들기
- tcpdump 로 패킷 까보기
- CLI에서 netstat으로 포트와 pid 찾아서 kill 해보기

[주요 개념]

`Selector` : 여러개의 채널에서 발생하는 **이벤트(연결이 생성됨, 데이터가 도착함 등)** 를 모니터링할 수 있는 객체

`ServerSocketChannel`: 들어오는 TCP 연결을 수신할 수 있고, 멀티 쓰레드에 안전한 채널

`SocketChannel`: 위의 채널에서 연결을 가져와서, accept() 메서드로 새로운 커넥션을 위한 socket channel 을 반환

`buffer.flip()`: 읽기/쓰기 작업의 범위를 설정 (시작-끝)

socketChannel(client)에 write(buffer) 전에 호출하여 클라이언트에게 데이터를 전송하기 전에 write mode 로 전환하는 역할

`read(buffer)` 후에 호출.

→ position을 0으로 세팅해서 버퍼의 시작부터 읽도록 함.

`java_home`:
java, javac 같은 자바 실행 파일을 찾는데 쓰는 환경 변수

## Kotlin으로 UnixDomainSocket 만들어서 메세지 송수신해보기

UnixDomainSocket이란?

- 동일한 호스트 OS 시스템 상에서 실행되는 프로세스 간에 데이터 교환을 할 때, 엔드포인트로 사용됨.
    - 반면, TCP/IP는 인터넷 상 혹은 private networks 에서 네트워크 간 커뮤니케이션임.
- IPC socket ([inter-process communication](https://ko.wikipedia.org/wiki/%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4_%EA%B0%84_%ED%86%B5%EC%8B%A0) socket) 이라고도 하며 TCP의 소켓과 동일한 API 로 데이타를 주고 받을 수 있는 local file 기반의 소켓
- TCP socket 과 차이점: local host 의 process 간의 통신이므로 속도가 매우 빠르고 메모리 소모가 적다

  네트워크

- OS 시스템이 제공해주는 기능으로, 대부분의 프로그래밍 언어에서  가지고 있음.

참고

https://kouzie.github.io/java/java-NIO/#java-nio

https://www.baeldung.com/java-nio-selector

https://www.lesstif.com/linux-core/unix-domain-socket