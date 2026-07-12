# Typography Design System & Styling Rules
**Project:** `id.my.masdepan.adatin`  
**Framework:** Android Native (Vanilla Kotlin & XML Views)  
**Version:** 1.0  

---

## 1. Overview & Core Philosophy

Our typography system is built on a clean, intentional contrast between two distinct typefaces: **Cinzel** and **Plus Jakarta Sans**. 

* **Cinzel (Display Serif):** Used strictly for high-impact brand statements, hero headers, and primary screen titles. It brings an elegant, timeless, and authoritative aesthetic.
* **Plus Jakarta Sans (Modern Sans-Serif):** Serves as the functional workhorse of the application. Used for section headers, sub-navigation, card titles, interactive buttons, and all body text. It prioritizes legibility, clean geometry, and modern UI readability.

### Key Rules
1. **Accessibility First (PX to SP Mapping):** Never hardcode text sizes in pixels (`px`) or density-independent pixels (`dp`). All typography specifications must use scale-independent pixels (`sp`) to respect user system accessibility settings.
2. **Strict Line Spacing:** All heading levels enforce a **1.1x line-height multiplier** (`android:lineSpacingMultiplier="1.1"`) to maintain compact, impactful multi-line titles without excessive vertical whitespace.
3. **Global Default:** Body text defaults to **Plus Jakarta Sans Regular** across the entire application theme to minimize redundant styling in individual layout files.

---

## 2. Font Assets & Directory Structure

All font files must be placed directly inside the Android resource directory: `app/src/main/res/font/`.

> [!IMPORTANT]
> **Android Naming Convention:** Android resource names must be strictly **lowercase**, alphanumeric, and use underscores only. Hyphens, uppercase letters, and spaces are invalid and will cause compilation failures.

### Required Font Weights
| Font Family | Weight Name | Numeric Weight | File Name | Primary Usage |
| :--- | :--- | :--- | :--- | :--- |
| **Cinzel** | Bold | 700 | `cinzel_bold.ttf` | Brand headers, Hero titles (H1–H3) |
| **Cinzel** | Regular | 400 | `cinzel_regular.ttf` | Decorative subtitles, quotes |
| **Plus Jakarta Sans** | Bold | 700 | `plus_jakarta_sans_bold.ttf` | Functional UI headers (H1–H6) |
| **Plus Jakarta Sans** | Medium | 500 | `plus_jakarta_sans_medium.ttf` | Button labels, emphasized body |
| **Plus Jakarta Sans** | Regular | 400 | `plus_jakarta_sans_regular.ttf` | Global app default, normal body text |

---

## 3. Typography Tokens & Scale

The table below maps our UI/UX design specifications directly to native Android XML scale tokens.

| Token Level | Design Spec (px) | Android Size (`sp`) | Line Spacing Multiplier | Equivalent LH (`sp`) | Primary Typeface | Typical Usage Context |
| :--- | :---: | :---: | :---: | :---: | :--- | :--- |
| **Heading 1** | 56 px | **56 sp** | `1.1` | ~61.6 sp | Cinzel / Jakarta Bold | Onboarding banners, primary hero titles |
| **Heading 2** | 48 px | **48 sp** | `1.1` | ~52.8 sp | Cinzel / Jakarta Bold | Main screen headers, major feature titles |
| **Heading 3** | 40 px | **40 sp** | `1.1` | ~44.0 sp | Cinzel / Jakarta Bold | Modal dialog titles, prominent statistics |
| **Heading 4** | 32 px | **32 sp** | `1.1` | ~35.2 sp | Plus Jakarta Sans Bold | Card group headers, section dividers |
| **Heading 5** | 24 px | **24 sp** | `1.1` | ~26.4 sp | Plus Jakarta Sans Bold | Sub-section titles, list item primary text |
| **Heading 6** | 20 px | **20 sp** | `1.1` | ~22.0 sp | Plus Jakarta Sans Bold | Card titles, form field group labels |
| **Body Large** | 16 px | **16 sp** | `1.4` | ~22.4 sp | Plus Jakarta Sans Regular | Primary article text, detailed descriptions |
| **Body Medium** | 14 px | **14 sp** | `1.4` | ~19.6 sp | Plus Jakarta Sans Regular | Default UI body text, list item subtext |
| **Caption / Label**| 12 px | **12 sp** | `1.3` | ~15.6 sp | Plus Jakarta Sans Medium | Timestamps, metadata, badges, helper text |

---

## 4. Vanilla Android XML Implementation Guide

### A. Global Theme Configuration (`res/values/themes.xml`)
To ensure all standard components (TextViews, Buttons, CheckBoxes, etc.) automatically inherit our primary clean sans-serif typeface without manual styling, declare the font family at the root theme level:

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Theme.Adatin" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <!-- Brand Colors -->
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorOnPrimary">@color/white</item>
        
        <!-- Global Typography Default -->
        <item name="android:fontFamily">@font/plus_jakarta_sans_regular</item>
    </style>
</resources>
```

### B. Style Definitions (`res/values/styles.xml`)
Define modular, reusable text appearance styles. Always inherit from `Widget.AppCompat.TextView` or Material equivalents to maintain compatibility.

```xml
<resources>
    <!-- ========================================== -->
    <!-- CINZEL STYLES (Display & Brand Headers)   -->
    <!-- ========================================== -->
    <style name="TextAppearance.Adatin.Cinzel.Heading1" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/cinzel_bold</item>
        <item name="android:textSize">56sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Cinzel.Heading2" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/cinzel_bold</item>
        <item name="android:textSize">48sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Cinzel.Heading3" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/cinzel_bold</item>
        <item name="android:textSize">40sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <!-- ========================================== -->
    <!-- PLUS JAKARTA SANS STYLES (Functional UI)   -->
    <!-- ========================================== -->
    <style name="TextAppearance.Adatin.Jakarta.Heading1" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">56sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Jakarta.Heading2" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">48sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Jakarta.Heading3" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">40sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Jakarta.Heading4" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">32sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Jakarta.Heading5" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">24sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>

    <style name="TextAppearance.Adatin.Jakarta.Heading6" parent="Widget.AppCompat.TextView">
        <item name="android:fontFamily">@font/plus_jakarta_sans_bold</item>
        <item name="android:textSize">20sp</item>
        <item name="android:lineSpacingMultiplier">1.1</item>
    </style>
</resources>
```

---

## 5. Usage in Layouts & Kotlin Code

### A. Applying in XML Layouts
Do not hardcode `textSize`, `fontFamily`, or `lineSpacingMultiplier` directly on individual layout elements. Apply the reusable style via the `style` attribute:

```xml
<!-- Example: Screen Title using Cinzel Heading 2 -->
<TextView
    style="@style/TextAppearance.Adatin.Cinzel.Heading2"
    android:id="@+id/txtScreenTitle"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Welcome to Adatin"
    android:textColor="?attr/colorOnSurface" />

<!-- Example: Section Header using Jakarta Heading 5 -->
<TextView
    style="@style/TextAppearance.Adatin.Jakarta.Heading5"
    android:id="@+id/txtSectionHeader"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:text="Recent Activities" />

<!-- Example: Body text inherits global theme font automatically -->
<TextView
    android:id="@+id/txtDescription"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="14sp"
    android:lineSpacingMultiplier="1.4"
    android:text="This text automatically uses Plus Jakarta Sans Regular." />
```

### B. Programmatic Styling in Kotlin (ViewBinding)
When dynamically altering typography in Kotlin (e.g., inside RecyclerView adapters, state changes, or custom view builders), use `.setTextAppearance()`:

```kotlin
import id.my.masdepan.adatin.R
import androidx.core.widget.TextViewCompat

// Modern approach (API 23+ compatible via TextViewCompat)
TextViewCompat.setTextAppearance(binding.txtDynamicHeader, R.style.TextAppearance_Adatin_Jakarta_Heading3)

// Or direct native call if minSdk >= 23
binding.txtDynamicHeader.setTextAppearance(R.style.TextAppearance_Adatin_Jakarta_Heading3)
```

---

## 6. Accessibility & Refactoring Best Practices

### Dynamic Type & Overflow Prevention
Because modern Android versions (Android 14/15/16+) support non-linear font scaling up to **200%** for visually impaired users, large display headings (Heading 1 at 56sp and Heading 2 at 48sp) can easily exceed screen boundaries if constrained incorrectly.

* **Never hardcode view heights:** Never set `android:layout_height="50dp"` on a `TextView`. Always use `wrap_content`.
* **ConstraintLayout horizontal constraints:** Ensure large titles have explicit horizontal constraints (`startToStart`, `endToEnd`) and set `android:layout_width="0dp"` (match constraint) with `android:hyphenationFrequency="normal"` if multi-line wrapping is expected.
* **Auto-sizing text (When appropriate):** For single-line headers that must never wrap (like numerical balance displays), use XML auto-sizing:
  ```xml
  <TextView
      style="@style/TextAppearance.Adatin.Cinzel.Heading1"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:maxLines="1"
      app:autoSizeTextType="uniform"
      app:autoSizeMinTextSize="24sp"
      app:autoSizeMaxTextSize="56sp" />
  ```

---

## 7. AI Refactoring System Prompt

Copy and paste this block into any AI assistant when converting legacy layout files or onboarding new team members to automate compliance with this typography system:

```text
You are an expert Android developer specializing in XML layout refactoring. I am providing you with an existing Android XML layout file. Your task is to refactor the TextView, Button, and other text-based UI components to strictly adhere to the Adatin Typography Design System.

Design System Styles Available (in res/values/styles.xml):
- "@style/TextAppearance.Adatin.Cinzel.Heading1" (56sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Cinzel.Heading2" (48sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Cinzel.Heading3" (40sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Cinzel.Heading4" (32sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Cinzel.Heading5" (24sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Cinzel.Heading6" (20sp, Serif Bold, 1.1x LH)
- "@style/TextAppearance.Adatin.Jakarta.Heading1" through Heading6 (Same sizes, Sans-Serif Bold, 1.1x LH)

Refactoring Rules:
1. Identify any text component that acts as a title, header, or prominent label based on its current textSize, text content, or ID.
2. Replace any hardcoded `android:textSize`, `android:fontFamily`, `android:textStyle`, and `android:lineSpacingMultiplier` attributes on those views with the appropriate `style="..."` attribute from the list above.
3. If a TextView represents a primary branding element, hero banner, or main screen title, use the Cinzel styles. If it is a functional UI section header, card title, or subheader, use the Jakarta styles.
4. Leave standard body text, captions, and small descriptions alone (they inherit the global app theme font automatically).
5. DO NOT alter any IDs, constraints, layout widths/heights, margins, padding, or data binding tags. Only modify typography-related attributes.
6. Output only the clean, refactored XML layout code without conversational filler.
```
