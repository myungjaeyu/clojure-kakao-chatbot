(ns kakao-chatbot.core
  (:use ring.util.response)
  (:require [compojure.core :refer :all]
            [compojure.handler :as h]
            [compojure.route :as route]
            [ring.middleware.json :as m-json]
            [ring.middleware.cors :refer [wrap-cors]]
            [clojure.core.match :refer [match]]))

(defn keyboard []
  (println "keyboard")
  {:type "buttons" :buttons ["선택 1" "선택 2" "선택 3"]})

(defn message [user_key type content]
  (println (str "message" " user_key : " user_key " type : " type " content : " content))
  (match [type content]
         ["text" "차량번호등록"]
         {:message {:text "귀하의 차량이 성공적으로 등록되었습니다. 축하합니다!"}}
         ["photo" _] ; ["photo" "http://photo_url/number.jpg"]
         {:message {:text "귀하의 차량이 성공적으로 등록되었습니다. 축하합니다!"
                    :photo {:url "https://photo.src"
                            :width 640
                            :height 480}}
                    :message_button {:label "주유 쿠폰받기"
                                     :url "https://coupon/url"}
          :keyboard {:type "buttons"
                     :buttons ["처음으로" "다시 등록하기" "취소하기"]}}
         :else nil))

(defn friend-join [user_key]
  (println (str "friend join" " user_key : " user_key))
  {:http_status_code 200
   :code 0
   :message "SUCCESS"
   :comment "정상 응답"})

(defn friend-block [user_key]
  (println (str "friend block" " user_key : " user_key))
  {:http_status_code 200
   :code 0
   :message "SUCCESS"
   :comment "정상 응답"})

(defn chat-room [user_key]
  (println (str "chat room" " user_key : " user_key))
  {:http_status_code 200
   :code 0
   :message "SUCCESS"
   :comment "정상 응답"})

(defroutes handler
           (GET "/keyboard" [] (response 
                                (keyboard)))
           (POST "/message" [user_key type content] (response
                                                     (message user_key type content)))
           (context "/friend" []
                    (POST "/" [user_key] (response 
                                          (friend-join user_key)))
                    (DELETE "/" [user_key] (response 
                                            (friend-block user_key))))
           (DELETE "/chat_room/:user_key" [user_key] (response
                                                      (chat-room user_key)))
           (route/not-found (response
                             {:message "not found"})))

(def app
  (-> (h/api handler)
      (m-json/wrap-json-params)
      (m-json/wrap-json-response)
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])))