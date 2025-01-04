# kotlin-blackjack
## 2단계 
### 기능 요구사항 
블랙잭 게임을 변형한 프로그램을 구현한다. 
블랙잭 게임은 딜러와 플레이어 중 카드의 합이 21 또는 21에 가장 가까운 숫자를 가지는 쪽이 이기는 게임이다.

- 카드의 숫자 계산은 카드 숫자를 기본으로 하며, 예외로 Ace는 1 또는 11로 계산할 수 있으며, King, Queen, Jack은 각각 10으로 계산한다.
- 게임을 시작하면 플레이어는 두 장의 카드를 지급 받으며, 두 장의 카드 숫자를 합쳐 21을 초과하지 않으면서 21에 가깝게 만들면 이긴다. 
  - 21을 넘지 않을 경우 원한다면 얼마든지 카드를 계속 뽑을 수 있다.


### 기능 목록
#### 입력  
- [x] 겜블러 이름 입력 (쉼표로 분리)
- [x] 카드를 더 받을 것인지 y/n 입력 

#### 출력 
- [x] 이름 입력 안내 메세지 
  - `게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)`
- [x] 겜블러에게 카드를 나누어 줬다고 알리는 메세지
  - `pobi, jason에게 2장의 카드를 나누었습니다.`
- [x] 겜블러가 받은 카드를 출력하는 메세지 
  - `pobi카드: 2하트, 8스페이드`
- [x] 결과를 출력하는 메세지 
  - `pobi카드: 2하트, 8스페이드, A클로버 - 결과: 21`

#### 비즈니스 로직 관련   
- 겜블러 
  - [x] 겜블러는 카드를 한장씩 받을 수 있다
  - [x] 겜블러는 여러장의 카드를 받을 수 있다  
  - [x] 카드를 받을 수 있는지 확인할 수 있다
- 점수 계산기
  - [x] 카드 목록의 점수를 합산할 수 있다
    - Ace 점수는 21점을 초과하지 않고, 21점과 가깝게 설정한다
    - "21"이라는 경계값은 어디에 둘까? 게임의 정책이다.
    - Ace 카드의 점수를 결정하는 것은 누가되는게 좋을까... 일단 구현 후 추후 고민..   
- 카드 등급 (CardRank) 
  - [x] Ace는 1 또는 11점이다.
  - [x] King, Queen, Jack은 각각 10이다.
- 카드 목록
  - [x] 카드는 조커를 제외하고 모두 52장이다. (스페이드/하트/클로버/다이아 각각 A~King 까지 13장씩) 
  - [x] 카드를 하나씩 뽑을 수 있다.
  - [x] 카드를 특정 방식으로 섞을 수 있다. 
    - 이 기능을 어디에 넣을까... 카드? 딜러? 카드 셔플러? 일단 카드를 만들 때 전략을 넣어주는 방법 채택 
- 딜러   
  - [x] 랜덤으로 섞인 카드를 가진다 
  - [x] 겜블러에게 카드를 2장씩 나누어 준다  
  - [x] 겜블러의 선택(y/n)에 따라 카드를 1장씩 나누어준다 
- 블랙잭 게임 룰
  - 모든 겜블러가 카드 받기를 원하지 않는 경우 종료
  - 플레이어의 카드 합은 21을 초과하지 않아야 한다. (초과 시 패배? 게임을 종료해야 하나?)
    - 21을 넘지 않을 경우, 얼마든지 카드를 뽑을 수 있다. 
  - 21을 초과하지 않으면서, 결과가 21에 가까운 쪽을 선택하는 것이 유리하다.
  - Ace 점수는 1 또는 11 중에서 플레이어가 자유롭게 선택할 수 있다.
    - ex: Ace를 제외한 카드 합이 20 일 경우, Ace는 1로 보는 것이 유리하다.
    - ex: Ace를 제회한 카드 합이 10일 경우, Ace는 11로 보는 것이 유리하다.
    - Ace는 가장 나중에 계산하면 될 듯 하다.


## 3단계 
### 기능 요구사항
- 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 하고, 17점 이상이면 추가로 받을 수 없다.
- 딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리한다.
- 게임을 완료한 후 각 플레이어별로 승패를 출력한다.

### 프로그래밍 요구사항 
- 3개 이상의 인스턴스 변수를 가진 클래스를 쓰지 않는다.
- 딜러와 플레이어에서 발생하는 중복 코드를 제거해야 한다.


### 기능 목록
#### 출력  
- [x] 딜러 카드 출력 
- [x] 딜러가 카드를 받았는지 여부를 출력 

#### 비즈니스 로직 관련  
- [x] 딜러는 처음 받은 두 개의 카드 합이 16 이하면 1장의 카드를 추가로 받아야 함
- [x] 딜러 카드가 21을 초과한 경우 플레이어들이 이김  


## 4단계 
### 기능 요구사항 
- 플레이어는 게임을 시작할 때 베팅 금액을 정해야 한다.
- 카드를 추가로 뽑아 21을 초과할 경우 베팅 금액을 모두 잃게 된다.
- 처음 두 장의 카드 합이 21일 경우 블랙잭이 되면 베팅 금액의 1.5 배를 딜러에게 받는다.
- 딜러와 플레이어가 모두 동시에 블랙잭인 경우 플레이어는 베팅한 금액을 돌려받는다.
- 딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리해 베팅 금액을 받는다.

### 기능 목록 
#### 출력 
- [] 배팅 금액 입력 안내 메세지 출력 

#### 입력 
- [] 플레이어의 배팅 금액 입력

#### 비즈니스 로직
- [x] 겜블러들은 배팅을 할 수 있다 
- 배팅 금액 정산
  - [] 무승부인 경우 플레이어가 배팅 금액을 돌려받는다
  - [] 플레이어가 이기는 경우 배팅 금액을 받는다 
    - [] 딜러가 21을 초과하면 플레이어들이 승리하므로 플레이어들은 배팅 금액을 돌려받는다
    - [] 딜러가 21점 이하이고 플레이어가 딜러보다 점수가 높으면 승리하므로 배팅 금액을 돌려받는다 
    - [] 플레이어의 카드가 2장이고 블랙잭(합이 21)인 경우 1.5배 받는다  
