(ns assignment.core
	(:require [ring.adapter.jetty      :as jetty]
			  [ring.middleware.reload  :refer [wrap-reload]]
			  [compojure.core :as compojure :refer [defroutes GET]]
			  [compojure.route  :as route :refer [not-found resources]]
			  [hiccup.core             :as h]
			  [hiccup.element          :as e]
			  [bultitude.core          :as b]
			  [assignment.misc         :as misc]
			  (compojure handler [route :as route])
        [ring.middleware.resource :refer (wrap-resource)]
        [clojure.test   :refer :all]
	)
)

(defn demo-vars
	[ns]
	{:namespace ns
	 :ns-name (ns-name ns)
	 :name (-> ns meta :name)
	 :route-prefix (misc/ns->context ns)
	 :page (ns-resolve ns 'page)
	}
)

(def extracting
	(->> (b/namespaces-on-classpath :prefix misc/ns-prefix)
          distinct
          (map #(do (require %) (the-ns %)))
          (map demo-vars)
          (filter #(:page %))
          (sort-by :ns-name)
    )
)

(defroutes landing
	(GET "/" req 
		(h/html [:html [:body [:h1 "Welcome to the Landing Page"]
						[:ol			
						(for [{:keys [name route-prefix]} extracting]
								[:li
									(e/link-to (str route-prefix "/") [:strong name])
								]
						)
						]
					]
				]
		)
	)
)

(defn- wrap-app-metadata
  [h app-metadata]
  (fn [req] (h (assoc req :demo app-metadata))))

(def app (apply compojure/routes
				landing
				(route/resources "/")
				(for [{:keys [page route-prefix] :as metadata} extracting]
				   (compojure/context route-prefix []
				   	(wrap-app-metadata (compojure/routes (or page (fn[_]))) metadata)
				   	)
				)
		   )
)

(defn -main
	[port-number]
	(jetty/run-jetty (wrap-reload #'app)
		{:port (Integer. port-number)}
	)
)
