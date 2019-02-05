(ns ^{:name "CONTACT"}
	assignment.contact
	(:require [assignment.misc :as misc]
			  [compojure.core  :refer [defroutes GET]]
			  [hiccup.page     :refer :all]
			  [hiccup.element  :as e]
			  [hiccup.core     :refer :all]
			  [assignment.core :refer :all]
	)
)

(defn contact
 	[req content]
 	(html [:body 
			content
			[:ol			
				(for [{:keys [name route-prefix]} extracting]
						[:li
							(e/link-to (str route-prefix "/") [:strong name])
						]
				)
			]
			[:p "Lorem Ipsum is simply dummy text of the printing and 
			typesetting industry. Lorem Ipsum has been the industry's standard dummy 
			text ever since the 1500s, when an unknown printer took a galley of type and 
			scrambled it to make a type specimen book. It has survived not only five centuries,
			but also the leap into electronic typesetting, remaining essentially unchanged. 
			It was popularised in the 1960s with the release of Letraset sheets containing 
			Lorem Ipsum passages, and more recently with desktop publishing software like 
			Aldus PageMaker including versions of Lorem Ipsum."]]
 		
 	)
)

(defroutes page
	(GET "/" req
		(contact req
			[:h1 "For contacting please mail us @ vikram@front.live.
				  Welcome to contact page"]
		)
	)
)
