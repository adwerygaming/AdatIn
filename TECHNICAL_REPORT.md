# Technical Project Report: AdatIn

## 1. Executive Summary
AdatIn is a specialized mobile application engineered using Kotlin and Material Design 3, designed to bridge the gap between Indonesian cultural heritage and modern digital accessibility. The platform functions as a robust marketplace for renting traditional Indonesian clothing (*Pakaian Adat*), streamlining the process for users to discover, evaluate, and manage rentals across a diverse range of regional styles. By digitizing the traditional rental ecosystem, AdatIn not only promotes the preservation of indigenous attire but also provides a structured, reliable transaction framework for both providers and customers.

The application’s technical architecture follows a modular, activity-based design pattern, leveraging high-performance components such as `RecyclerView` with custom adapters and `Coil` for optimized asynchronous image loading. The user journey is engineered for operational efficiency, featuring a seamless flow from authenticated entry to dynamic product discovery. Technical highlights include real-time search filtering, complex cart state management, and a comprehensive transaction lifecycle—encompassing checkout, payment processing, and multi-stage fulfillment tracking (pickup/return). This end-to-end engineering ensures a scalable and intuitive experience for cultural enthusiasts and event participants alike.

## 2. Codebase Structure
```text
├── app/src/main/java/id/my/masdepan/adatin/
│   ├── adapter/
│   │   ├── MyCartAdapter.kt - Cart item rendering
│   │   ├── MyTransactionAdapter.kt - Order history binding
│   │   ├── PakaianAdapter.kt - Catalog item rendering
│   │   └── TeamAdapter.kt - Staff profile display
│   ├── model/
│   │   └── Team.kt - Staff data class
│   ├── BottomNav.kt - Navigation logic helper
│   ├── CheckoutActivity.kt - Order processing flow
│   ├── ConfirmCancelActivity.kt - Cancellation validation logic
│   ├── ConfirmPickupActivity.kt - Pickup verification logic
│   ├── ConfirmReturnActivity.kt - Return verification logic
│   ├── DetailItemActivity.kt - Product specification view
│   ├── EditProfileActivity.kt - User data modification
│   ├── Extentions.kt - Utility extension functions
│   ├── GlobalFunction.kt - Shared business logic
│   ├── GlobalVariable.kt - Persistent state constants
│   ├── LoginActivity.kt - User authentication gateway
│   ├── MainActivity.kt - Core discovery hub
│   ├── MyCart.kt - Cart data model
│   ├── MyCartActivity.kt - Cart management interface
│   ├── MyProfileActivity.kt - User account dashboard
│   ├── MyTransaction.kt - Transaction data model
│   ├── MyTransactionActivity.kt - Rental history tracking
│   ├── OurTeamActivity.kt - Developer information page
│   ├── Pakaian.kt - Product data model
│   ├── PaymentActivity.kt - Transaction settlement gateway
│   ├── PaymentSuccessActivity.kt - Completion confirmation view
│   ├── RegisterActivity.kt - User onboarding process
│   ├── SearchProductActivity.kt - Dynamic filtered searching
│   ├── SplashScreen.kt - Brand identity entry
│   └── UserAccount.kt - Profile data model
├── app/src/main/res/layout/
│   ├── activity_main.xml - Primary landing UI
│   ├── activity_detail_item.xml - Product details UI
│   ├── activity_checkout.xml - Transaction workflow UI
│   ├── item_pakaian.xml - Catalog card layout
│   └── item_keranjang.xml - Cart list layout
```

## 3. Feature Specifications

### UI/UX Framework
* **Material Design 3 (M3):** Implementation for modern UI aesthetics and theming.
* **Responsive Lists:** Custom RecyclerView architectures for fluid data presentation.
* **Dynamic Filtering:** ChipGroup implementation for real-time category sorting.
* **Image Management:** Asynchronous loading and caching via the Coil library.

### Transaction & State Management
* **Checkout Pipeline:** End-to-end order flow with state persistence.
* **Cart Logic:** Supporting real-time quantity updates and price calculations.
* **Fulfillment Tracking:** Multi-stage lifecycle (Pending, Active, Returned).
* **Validation Workflows:** Integrated pickup and return verification logic.

### User Management
* **Authentication:** Session-based entry gateway (Login/Register).
* **Profile System:** Dynamic user data modification and account dashboards.
* **Global State:** Shared constants and utility functions for consistent session handling.

### Navigation Architecture
* **Intent Routing:** Activity-based navigation with secure data bundling.
* **Custom BottomNav:** Centralized access point for core app features.
* **Performance Branding:** Optimized Splash Screen for initial resource warm-up.
