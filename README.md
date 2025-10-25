<h1 align="center">Pilldroid</h1>

Pilldroid is a theoretical medication stock management application for people living in France.

<!--<a href="https://f-droid.org/packages/net.foucry.pilldroid">
    <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
    alt="Get it on F-Droid"
    height="80">
</a>-->

## What is Pilldroid?

Pilldroid is an applicaton for theoretical stock management of medicines on Android.

## Why "Theoretical stock"?

Pilldroid doesn’t know (and has no way of knowing) whether or not you have taken your
medicines. Pilldroid performs, simple calculation : `known stock - taken by
day`. This calculation is done every day between 11 AM and noon.


## What permissions does the Pilldroid app need?

- Pilldroid needs to know that the phone was restarted for itself
start his daily wake-up cycle.
- Pilldroid needs access to your phone’s camera for
  scan the barcode of the medicine boxes.
- Pilldroid needs to be able to send you notifications.
- Pilldroid needs to vibrate the phone for notifications.

## Does the Pilldroid app ship with third-party libraries?

Yes. For barcode reading, Pilldroid uses the project
[zxing](https://github.com/journeyapps/zxing-android-embedded) itself free and
open.

## Does the Pilldroid app contain trackers?

[NO !](https://reports.exodus-privacy.eu.org/fr/reports/net.foucry.pilldroid/latest/)

## Where does Pilldroid’s data come from?

They come from several files of the Agency for the Security of
Medicines, aggregated in a database through python script which is available in the past on this repository [TransformMeds](https://github.com/jfoucry/TransformMeds).

## How contribute to Pilldroid ?

This project is a fork of [Pilldroid](https://github.com/jfoucry/Pilldroid), the initial developer has died in september 2025 and I want to continue to maintain the project (only for maintenance).
