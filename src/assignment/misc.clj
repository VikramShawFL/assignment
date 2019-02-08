(ns assignment.misc
	(:import java.net.URI)
  (:require [clojure.string :as str])
)

(def ns-prefix "assignment")
(defn ns->context
  [ns]
  (str "/" (-> ns ns-name name (subs (inc (count ns-prefix))))
  )
)