# Technical Project Report: AdatIn

## 1. Executive Summary

AdatIn is a specialized mobile application engineered for the Android platform, designed to facilitate the rental of traditional Indonesian clothing (*Pakaian Adat*). The primary objective of the application is to preserve and promote Indonesian cultural heritage by lowering the barrier to accessing traditional garments for cultural ceremonies, educational events, and festivals. By providing a digitized marketplace for traditional attire, the application bridges the gap between local vendors and consumers, thereby fostering cultural appreciation and supporting local artisanal economies through a modern, accessible interface.

From a technical perspective, the application implements a structured, activity-based architecture utilizing Kotlin and XML layouts built on Material Design 3 guidelines. The user journey begins with user authentication, leading to a home dashboard that serves as the entry point for catalog discovery. Users can search and filter garments by name or region, view detailed descriptions and pricing, manage items within a local shopping cart state, and proceed to a detailed checkout flow. The application accommodates both home delivery and self-pickup options, complete with a transactional state machine that simulates order processing, shipping, and pickup confirmation asynchronously.

### Simplified Executive Summary (For End Users)

AdatIn is a simple and user-friendly mobile app designed to help you rent traditional Indonesian outfits (known as *Pakaian Adat*) directly from your phone. Whether you need a costume for a school event, a wedding, or a cultural festival, AdatIn makes it easy to find and rent the perfect outfit without the hassle of visiting multiple physical rental shops. Our goal is to make it easy for everyone to wear and celebrate Indonesia's rich cultural heritage.

Using the app is straightforward: you start by creating a simple account and logging in. From there, you can browse through a wide selection of traditional clothes, search for specific outfits, or filter them by the region they come from. Once you find an outfit you like, you select your size, add it to your shopping cart, and choose whether you want it delivered directly to your home or if you prefer to pick it up yourself. You can then track the status of your order—from processing and delivery to return—all in one place.

## 2. Codebase Structure (Directory Mapping)

```
app/src/main/
├── AndroidManifest.xml - Application manifest configuration
├── java/id/my/masdepan/adatin/
│   ├── BottomNav.kt - Bottom navigation helper
│   ├── CheckoutActivity.kt - Order checkout controller
│   ├── ConfirmCancelActivity.kt - Cancellation confirmation controller
│   ├── ConfirmPickupActivity.kt - Pickup confirmation controller
│   ├── ConfirmReturnActivity.kt - Return confirmation controller
│   ├── DetailItemActivity.kt - Product details controller
│   ├── EditProfileActivity.kt - Profile editor controller
│   ├── Extentions.kt - Utility extension functions
│   ├── GlobalFunction.kt - Global business logic
│   ├── GlobalVariable.kt - Global session state
│   ├── LoginActivity.kt - User login controller
│   ├── MainActivity.kt - Main catalog controller
│   ├── MyCart.kt - Cart data class
│   ├── MyCartActivity.kt - Shopping cart controller
│   ├── MyProfileActivity.kt - Profile dashboard controller
│   ├── MyTransaction.kt - Transaction data class
│   ├── MyTransactionActivity.kt - Transaction list controller
│   ├── OurTeamActivity.kt - Team info controller
│   ├── Pakaian.kt - Catalog item class
│   ├── PaymentActivity.kt - Payment processing controller
│   ├── PaymentSuccessActivity.kt - Payment success controller
│   ├── RegisterActivity.kt - User registration controller
│   ├── SearchProductActivity.kt - Product search controller
│   ├── SplashScreen.kt - App launch controller
│   ├── UserAccount.kt - User account model
│   ├── adapter/
│   │   ├── MyCartAdapter.kt - Cart adapter rendering
│   │   ├── MyTransactionAdapter.kt - Transaction adapter rendering
│   │   ├── PakaianAdapter.kt - Catalog adapter rendering
│   │   └── TeamAdapter.kt - Team adapter rendering
│   └── model/
│       └── Team.kt - Team data class
└── res/
    ├── layout/
    │   ├── activity_checkout.xml - Checkout layout UI
    │   ├── activity_confirm_cancel.xml - Cancel layout UI
    │   ├── activity_confirm_pickup.xml - Pickup layout UI
    │   ├── activity_confirm_return.xml - Return layout UI
    │   ├── activity_detail_item.xml - Detail layout UI
    │   ├── activity_edit_profile.xml - Profile edit layout UI
    │   ├── activity_login.xml - Login layout UI
    │   ├── activity_main.xml - Home layout UI
    │   ├── activity_my_cart.xml - Cart layout UI
    │   ├── activity_my_profile.xml - Profile layout UI
    │   ├── activity_my_transaction.xml - Transaction list UI
    │   ├── activity_our_team.xml - Team list UI
    │   ├── activity_payment_success.xml - Success layout UI
    │   ├── activity_payment.xml - Payment layout UI
    │   ├── activity_register.xml - Register layout UI
    │   ├── activity_search_product.xml - Search layout UI
    │   ├── activity_splash.xml - Splash layout UI
    │   ├── dialog_loading.xml - Loading dialog UI
    │   ├── item_filter_chip.xml - Filter chip UI
    │   ├── item_keranjang.xml - Cart item UI
    │   ├── item_pakaian.xml - Catalog item UI
    │   ├── item_team.xml - Team item UI
    │   └── item_transaksi.xml - Transaction item UI
    └── menu/
        └── bottom_nav_menu.xml - Navigation menu UI
```

## 3. Feature Specifications

* **User Authentication & Session Management**
  * Persistent user session validation via local shared preferences.
  * Secure user registration and login input validation handlers.
* **UI/UX & Dynamic Asset Binding**
  * Modern user interface built with XML layouts conforming to Material Design 3 guidelines.
  * Bottom navigation integration facilitating seamless activity transitions.
  * Asynchronous image loading and caching using the Coil library, complete with loading and error placeholder fallbacks.
* **Data Management & Search**
  * Dynamic catalog search and regional filtering of garments.
  * In-memory cart state management enabling dynamic item addition, quantity modification, and item removal.
  * Session-based user account and purchase history data structures.
* **Transaction & Order Lifecycle Tracking**
  * Comprehensive checkout flow supporting delivery fee calculation and pickup configurations.
  * Transaction status state machine simulating statuses from processing and shipping to pickup, completion, and cancellation.
  * Asynchronous state transitions executed via Kotlin coroutines to simulate real-world order processing delays.