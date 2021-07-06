# ContactGalleryAndRain

> 김윤재, 박민경



### Overview

> contact, gallery, rain_notification 기능을 담은 어플리케이션이다.



### Main



### Tab1











### Tab2: Gallery

> 여러 장의 이미지를 갤러리처럼 보여주는 탭으로,  이미지를 터치하면 해당 이미지만 확대하여 볼 수 있다.

<이미지 1>

* 초기 화면은 1개의 GridView로 이미지를 나타냄

  * GridView는 bitmap 객체로 정의된 ImageView로 구성되어 있음

  * 이미지 파일은 res/drawable 폴더에 정의되어 있음

<이미지 2>

* 특정 이미지 터치 시 확대
  * ImageActivity 클래스를 정의하여 확대된 사진을 보여줌





### Tab3: Rain_notification

> 비가 오는 날 사용자가 우산을 챙겨갈 수 있도록 Notification을 보낸다.

<이미지 1>

* 초기 화면은 TimePicker, Button, 사용자의 현재 위치정보로 이루어짐
  * 위치정보

    첫 실행 시, 사용자의 위치 권한 승인을 받아 LocationManager로 위도, 경도를 얻음

    Geocoder를 이용하여 위도, 경도 값을 주소로 변환하여 화면에 출력

  * TimePicker & Switch

    알림을 받기 원하는 시간으로 TimePicker을 설정하고 Switch를 on함

    TimePicker를 재설정할 시, Switch가 자동으로 off되고 기존에 설정되어 있던 알림은 자동으로 삭제됨

    

<이미지 2>

* Notification은 비가 오는 날, 사용자가 설정한 시간에 활성화됨.

  * 알림 조건

    기상청 날씨 API를 사용하여 현재 위치, 설정된 시간을 기반으로 강수확률을 얻음

    강수확률이 30% 이상인 날에 AlarmManager를 이용하여 사용자에게 notify함.

  * 알림 수신

    화면이 lock된 상태에서 알림 수신 시, 알림 소리와 함께 화면이 켜짐

    화면이 켜진 상태에서 알림 수신 시, 알림 소리와 함께 헤드업 알림(Heads up Notification)이 보임

  * 알림창 터치 시 앱이 실행됨

  * BroadCastReceiver를 이용하여 기기 재부팅 시에도 알림은 유지되도록 함

    