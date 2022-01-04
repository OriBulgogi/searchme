# searchme
#### 식품안전나라 레시피 정보api를 활용한 메뉴추천과 레시피제공 android native app
#### setting
- others 폴더의 sql 실행, php코드를 자신의 호스팅된 서버에 추가
- php 코드내의 접속정보 변경
  ```
      $con = mysqli_connect("localhost", "id", "password", "DBname");
  ```
- android Login.kt, Registeration.kt 코드의 호스팅정보 변경 : baseUrl 을 자신이 호스팅한 주소로 변경
  ```
            val retrofit = Retrofit.Builder()
            .baseUrl("yours Url") 
            .addConverterFactory(GsonConverterFactory.create())
            .build()
  ```          
  
#### 프로젝트 개요

남은 식재료로 만들 수 있는 요리를 매번 생각하는 일은 부담으로 다가온다. 내가 가지고 있는 식재료로 
만들 수 있는 요리를 출력해주는 서비스가 있다면 출력해준 요리 중 하나를 선택하면 되기 때문에 위 문제를 해결할 수 있다.



