# portfolio-android

## Features

* Technical
  * Fully written in Kotlin, AndroidX und Jetpack Compose
  * Android build-scripts with Kotlin-DSL
  * Localized in English and German
  * Check maven dependency updates with [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin)

* Home-Screen with shortcuts to different app features
  ![homescreen.png](static/images/homescreen.png)

* Timer
  ![timer.png](static/images/timer.png)
  * Count time timer, that supports Hours, Minutes and Seconds
  * Shows remaining seconds in a notification (Foreground Service)
  * Preserves the state of the timer when the app is closed

* Takeaway Tracker
  * Daily notification to remind you to track your takeaway
    ![takeawaytracker_notification.png](static/images/takeawaytracker_notification.png)
    * Click navigates to book screen
    * Back button than navigates to list screen
  * Track your takeaway
    ![takeawaytracker_book.png](static/images/takeawaytracker_book.png)
    * Form-Validation
  * List of tracked takeaways
    ![takeawaytracker_list.png](static/images/takeawaytracker_list.png)
  * Storage implementations:
    * In-Memory
    * Server-Based (with REST-Service) => TODO

* MCDonalds Coupons
  ![mcdonalds_coupons.png](static/images/mcdonalds_coupons.png)
  * Uses the JSON-data from mcdonalds.de to show the current coupons


## References

* Design based on [Meditation UI of Philipp Lackner](https://www.youtube.com/watch?v=g5-wzZUnIbQ)
