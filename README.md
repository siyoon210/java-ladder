# 사다리 게임
## 진행 방법
* 사다리 게임 게임 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/nextstep-step/nextstep-docs/tree/master/codereview)

## 2단계 - 사다리(생성)
### 기능 요구사항
- 사다리 게임에 참여하는 사람에 이름을 최대5글자까지 부여할 수 있다. 사다리를 출력할 때 사람 이름도 같이 출력한다.
- 사람 이름은 쉼표(,)를 기준으로 구분한다.
- 사람 이름을 5자 기준으로 출력하기 때문에 사다리 폭도 넓어져야 한다.
- 사다리 타기가 정상적으로 동작하려면 라인이 겹치지 않도록 해야 한다.
- |-----|-----| 모양과 같이 가로 라인이 겹치는 경우 어느 방향으로 이동할지 결정할 수 없다.

### 프로그래밍 요구사항
- 자바 8의 스트림과 람다를 적용해 프로그래밍한다.
- 규칙 6: 모든 엔티티를 작게 유지한다.

Q) 기본타입을 포장한 래핑클래스의 value가 불변원시타입이라면 getter없이 public으로 노출시키는건 어떤가요? 예를들어 지금 Height의 value는 public 한 필드로 노출시켰습니다.

### 2단계 피드백 사항
- [x] 메소드는 동사형으로 시작되어야 합니다~! (ApplicationExecutor)
- [x] 래핑된 value의 getter()
- [x] names 를 만드는 부분도 메소드로 분리해보면 좋을 것 같아요!
- [x] View 에서 사용되는 문자열도 별도 상수로 관리되면 좋을 것 같아요!
- [x] Ladder 는 모델 계층에 속해있으므로 View 와 관련된 로직은 분리되어야 하지 않을까요~?

## 3단계 - 사다리(게임 실행)
### 기능 요구사항
- 사다리 실행 결과를 출력해야 한다.
- 개인별 이름을 입력하면 개인별 결과를 출력하고, "all"을 입력하면 전체 참여자의 실행 결과를 출력한다.

### 프로그래밍 요구사항
- 자바 8의 스트림과 람다를 적용해 프로그래밍한다.
- 규칙 6: 모든 엔티티를 작게 유지한다.
- 규칙 7: 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.

### 3단계 피드백 사항
- [x] 파라미터 이름으로 축약어 사용은 최대한 지양하는 것이 어떨까요~? (getNextIndexOf(int currIndex))
- [x] NumberOfParticipants 값을 넘겨주는 것은 어떨까요~? (private Results(List<String> value, Participants participants) {)
    - 그러네요. 굳이 필요없는 도메인에게 의존성을 갖게 되는 문제가 있었군요! :)
- [x] Results 라는 이름은 ExecutionResults 의 상위 개념으로 오해할 수 도 있을 것 같아요.
    - Results에 이름을 ResultCandidate라는 이름으로 변경했습니다. 이름짓기는 정말 어렵네요 ㅠㅠ 혹시 적절했던 사례가 있었으면 얘기해주실수 있나요? :) 
- [x] allKeyword 에 대한 판단을 view 에서 했을때와 view 외부에서 계층에서 했을 때의 각각의 장단점은 무엇일까요~?
    - AllKeyword에 대한 고민해봤습니다. 일단 'all'이라는 키워드를 도메인이 갖고 판단하는게 적절하지 못하게 보이네요. 
    입력 키워드와 키워드에 판단은 view에서 해야하는데 도메인이 갖게 되었으니까요. 이건 view에 대한 직접적인 의존이 아니지만, 간접적으로 도메인이 view에 로직에 의존하게 되어버리는것 같습니다. 
    keyword에 판단은 입력값을 건내준 ConsoleInputView로 이동시켰습니다.
    참석자 이름중에 allKeyword와 같은 이름이 있는지에 대한 유효성검사를 Participants에서 검사했었는데, Keyword를 view가 가지게 되어서 해당 검사는 LadderGame으로 이동시켰습니다. :)
    이런 의도로 리뷰를 해주신게 맞을까요? 혹시 더 고민해봐야할 것이 있다면 꼭 얘기해주세요! 감사합니다.
    
## 4단계 - 사다리(리팩토링)

### 4단계 피드백 사항
- [x] 어떤일을 수행하는 function 인지 정보가 드러나면 더 좋을 것 같습니다! (Ladder:17)
- [x] 해당 init 메소드의 구현내용을 아래 메소드를 재사용하는 방식으로 바꿔보는 것은 어떨까요~? (LadderLine)
- [x] 저는 개인적으로 이렇게 메소드에 사용된 변수를 다시 결과로 재사용하게 되면 계속해서 변수의 변경과정을 추적해야하는 리소스가 들어서 재사용하지 않고 각각의 의미를 담은 변수를 선언하여 사용하곤 합니다 :) (LadderLine:27)