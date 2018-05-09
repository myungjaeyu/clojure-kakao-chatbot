## API specifications

- 카카오 플러스친구 API 서버에서 개발사 서버를 호출하는 API에 대한 명세서입니다.


#### Home Keyboard API

##### Specification
- **Method** : GET
- **URL** : http(s)://:your\_server\_url/keyboard
- **Content-Type** : application/json; charset=utf-8
- **예제**
```
curl -XGET 'https://:your_server_url/keyboard'
```
- **Response**

| 필드명 | 타입 | 필수여부 | 설명 |
| ---- | ---- | -------- | ----------- |
| keyboard | Keyboard | Required | 키보드 영역에 표현될 버튼에 대한 정보. 생략시 ```text``` 타입이 선택된다.|
- **예제**
```
{
    "type" : "buttons",
    "buttons" : ["선택 1", "선택 2", "선택 3"]
}
```

#### 메시지 수신 및 자동응답 API

##### Specification
- **Method** : POST
- **URL** : http(s)://:your\_server\_url/message
- **Content-Type** : application/json; charset=utf-8
- **Parameters**

| 필드명 | 타입 | 필수여부 | 설명 |
| ---- | ---- | -------- | ----------- |
| user\_key | String | Required | 메시지를 발송한 유저 식별 키 |
| type | String | Required | text, photo |
| content | String | Required | 자동응답 명령어의 메시지 텍스트 혹은 미디어 파일 uri |
- **예제**
```
curl -XPOST 'https://:your_server_url/message' -d '{
  "user_key": "encryptedUserKey",
  "type": "text",
  "content": "차량번호등록"
}'
```
```
curl -XPOST 'https://your_server_url/message' -d '{
  "user_key": "encryptedUserKey",
  "type": "photo",
  "content": "http://photo_url/number.jpg"
}'
```
- **Response**

| 필드명 | 타입 | 필수여부 | 설명 |
| ---- | ---- | -------- | ----------- |
| message | Message | Required | 자동응답 명령어에 대한 응답 메시지의 내용. 6.2에서 상세 기술 |
| keyboard | Keyboard | Optional | 키보드 영역에 표현될 명령어 버튼에 대한 정보. 생략시 text 타입(주관식 답변 키보드)이 선택된다. 6.1에서 상세 기술|
- **예제**
```
{
    "message":{
        "text" : "귀하의 차량이 성공적으로 등록되었습니다. 축하합니다!"
    }
}
```
```
{
  "message": {
    "text": "귀하의 차량이 성공적으로 등록되었습니다. 축하합니다!",
    "photo": {
      "url": "https://photo.src",
      "width": 640,
      "height": 480
    },
    "message_button": {
      "label": "주유 쿠폰받기",
      "url": "https://coupon/url"
    }
  },
  "keyboard": {
    "type": "buttons",
    "buttons": [
      "처음으로",
      "다시 등록하기",
      "취소하기"
    ]
  }
}
```

#### 친구 추가/차단 알림 API

##### Specification
- **Method** : POST / DELETE
- **URL** : http(s)://:your\_server\_url/friend
- **Content-Type** : application/json; charset=utf-8
- **Parameters**

| 필드명 | 타입 | 필수여부 | 설명 |
| ---- | ---- | -------- | ----------- |
| user\_key | String | Required | 유저 식별키 |
- **Response**

| http status code | code | message | comment |
| ---------------- | ---- | ------- | ------- |
| 200 | 0 | SUCCESS | 정상 응답 |
- **예제**
 - *친구 추가*
```
curl -XPOST 'https://:your_server_url/friend' -d '{"user_key" : "HASHED_USER_KEY" }'
```
 - *친구 삭제*
```
curl -XDELETE 'https://:your_server_url/friend/:user_key'
```

#### 채팅방 나가기

##### Specification
- **Method** : DELETE
- **URL** : http(s)://:your\_server\_url/chat\_room/:user\_key
- **Content-Type** : application/json; charset=utf-8
- **Response**

| http status code | code | message | comment |
| ---------------- | ---- | ------- | ------- |
| 200 | 0 | SUCCESS | 정상 응답 |
- **예제**
```
curl -XDELETE 'https://:your_server_url/chat_room/HASHED_USER_KEY'
```