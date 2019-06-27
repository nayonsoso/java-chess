# java-chess

## todo
### 1단계
### 2단계
### 3단계
    

## done
### 1단계
    * Row를 구현하여야 한다. Row는 1~8까지의 숫자를 가진다.
    * Column을 구현하여야 한다. Column은 a~f까지의 문자를 가진다.
    * Row와 Column을 필드로 가지는 Position 객체를 생성하여야 한다.
    * Piece 인터페이스를 만들고 그것을 구현하는 (King,Queen,Knight,Rook,Bishop,Pawn)클래스를 생성하여야 한다.
    * Piece를 리스트로 가지는 Board 객체를 생성하여야 한다.
      
### 2단계
    * 현재 위치와 움직일 위치가 유효한 위치인지를 판단하여야 한다.
    * 현재 위치에 Piece가 있는지 판단하여야 한다.
    * 움직일 위치에 우리팀 말이 있는지 판단하여야 한다.
    * Direction 인터페이스를 만들고 그것을 구현하는 방향 클래스를 생성하여야 한다.
    * Navigator를 구현하여야 한다.
            * 현재 위치와 움직일 위치를 바탕으로 Direction을 결정하여야 한다.
            * 결정된 Direction으로 움직일 횟수를 결정하여야 한다.
    * 움직일 위치가 이동가능한지 판단하여야 한다.
            * 해당 말이 이동 가능한 방향과 횟수인지 판단하여야 한다.
            * 이동 경로 내에 다른 말이 있는지 판단하여야 한다.
    * 현재 위치와 움직일 위치를 입력받으면 현재 위치에 있는 Piece를 움직일 위치로 움직여야한다.        