(ns ^{:name "BLOG"}
	assignment.blog
	(:require [assignment.misc :as misc]
			  [compojure.core  :refer [defroutes GET]]
			  [hiccup.page     :refer :all]
			  [hiccup.element  :as e]
			  [hiccup.core     :refer :all]
			  [assignment.contact :as con]
	)
)

; (defroutes page
; 	(GET "/" req
; 		(html [:body [:h1 "Welcome to the blog page"]])
; 	)
; )

(def page 
	(GET "/" req
		(con/contact req
			[:h1 "Start Blogging"]
		)
	)
)
