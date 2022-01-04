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
##### - preview (best case)
![image](https://user-images.githubusercontent.com/81247213/148017281-86f56640-cb28-4055-8a70-4af89139cf07.png)

##### - UI
1. login 

![image](https://user-images.githubusercontent.com/81247213/148018721-9b7eaba9-427d-4216-9cba-543d91bbd329.png)

2. Registration
- 선호하는 조리법, 자신의 알러지정보 등록가능 해당 정보는 레시피 추천시 알러지 재료 제외, 선호도에 따른 출력 순서 반영되어짐 
- 
![image](https://user-images.githubusercontent.com/81247213/148018865-3a5e8db9-1917-41a8-ba68-d4fa51b1bbe8.png)

3. 재료선택
- 가지고있는 재료를 선택
- 
![image](https://user-images.githubusercontent.com/81247213/148019301-69addf58-7989-4d43-80c3-e7abf6ef18ea.png)

4. 메뉴선택
- 일치하는 재료, 부족한 재료를 알려주고 메뉴이름버튼을 누르면 레시피화면으로 이동
- 
![image](https://user-images.githubusercontent.com/81247213/148019345-3660aac2-ce50-41e3-bd0e-f6cdd1bddc5b.png)

![image](https://user-images.githubusercontent.com/81247213/148019411-beff3058-0dc7-49af-ba70-5fe22c21fc89.png)


5. 레시피
- 영양정보와 조리법을 순서대로 알려준다.
- 
![image](https://user-images.githubusercontent.com/81247213/148019447-64bbe624-90f3-43a9-9b15-b9382c5a6b96.png)

![image](https://user-images.githubusercontent.com/81247213/148019503-6f483c1e-0906-42cc-b415-dfbcfd873f4b.png)

![image](https://user-images.githubusercontent.com/81247213/148019481-642aabe7-c5b1-43b5-9fdc-56f2c5aaefa1.png)

##### Reference
> retrofit2 - https://stickode.tistory.com/43
> retrofit2 - https://square.github.io/retrofit/
> 서적 - 이것이 안드로이드다 with 코틀린 
> https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView
> https://developer.android.com/reference/android/content/Intent
> https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder
> https://developer.android.com/training/volley


