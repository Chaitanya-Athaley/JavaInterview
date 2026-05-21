# Abstract Factory Pattern - Simple Explanation

## 🎯 What is Abstract Factory Pattern?

**In Simple Words:**
Abstract Factory creates **families of related objects** while keeping them **consistent** with each other.

Think of it like:
- **Furniture Store**: One store sells Italian furniture (Italian chairs, Italian tables, Italian cabinets). Another store sells Scandinavian furniture (Scandinavian chairs, Scandinavian tables, Scandinavian cabinets). Both stores have the same product types, but each maintains their own style consistency.

## 📱 Real-World Example: Mobile UI Themes

```
User's App Screen
    ↓
Decision: iOS or Android?
    ↓
    iOS Theme              Android Theme
    ↓                      ↓
    iOSFactory            AndroidFactory
    ↓                      ↓
    Creates:              Creates:
    - iOSButton           - AndroidButton
    - iOSTextbox          - AndroidTextbox
    - iOSMenu             - AndroidMenu
    ↓                      ↓
    Consistent iOS UI     Consistent Android UI
```

## 🏗️ Structure

```
┌─────────────────────────────────────┐
│     Abstract Factory Interface      │
│  (UIFactory)                        │
│  + createButton()                   │
│  + createTextbox()                  │
│  + createMenu()                     │
└─────────────────────────────────────┘
         △           △
         │           │
         │           │
    ┌────┴───┐  ┌────┴────────┐
    │         │  │             │
┌───┴──┐  ┌──┴──┐         ┌───┴──────┐
│      │  │     │         │          │
iOSFactory  AndroidFactory  ...OtherFactory
│      │  │     │         │          │
└───┬──┘  └──┬──┘         └───┬──────┘
    │        │                │
    Creates: │                │ Creates:
    - iOS    │                │ - Other
      Button │                │   Button
    - iOS  Creates:           │ - Other
      Textbox - Android         Textbox
    - iOS    Button           │ - Other
      Menu  - Android           Menu
           Textbox
           - Android
             Menu
```

## 🔑 Key Differences: Factory vs Abstract Factory

| Aspect | Factory Pattern | Abstract Factory |
|--------|-----------------|------------------|
| **Purpose** | Create single product | Create family of related products |
| **Scope** | One product type | Multiple related product types |
| **Example** | Create any Vehicle | Create iOS Button + Textbox + Menu together |
| **Consistency** | Not guaranteed | Guaranteed - family stays consistent |
| **Complexity** | Simple | More complex |

## 📋 Files in Example

| File | Purpose |
|------|---------|
| `Button` | Abstract product interface |
| `Textbox` | Abstract product interface |
| `Menu` | Abstract product interface |
| `iOSButton` | Concrete iOS button |
| `AndroidButton` | Concrete Android button |
| `iOSFactory` | Creates all iOS components |
| `AndroidFactory` | Creates all Android components |
| `Application` | Client that uses factory |

## 💡 How It Works

### Step 1: Define Abstract Products
```java
interface Button { ... }
interface Textbox { ... }
interface Menu { ... }
```

### Step 2: Create Concrete Products
```java
class iOSButton implements Button { ... }
class iOSTextbox implements Textbox { ... }
class iOSMenu implements Menu { ... }

class AndroidButton implements Button { ... }
class AndroidTextbox implements Textbox { ... }
class AndroidMenu implements Menu { ... }
```

### Step 3: Define Abstract Factory
```java
interface UIFactory {
    Button createButton();
    Textbox createTextbox();
    Menu createMenu();
}
```

### Step 4: Create Concrete Factories
```java
class iOSFactory implements UIFactory {
    Button createButton() { return new iOSButton(); }
    Textbox createTextbox() { return new iOSTextbox(); }
    Menu createMenu() { return new iOSMenu(); }
}

class AndroidFactory implements UIFactory {
    Button createButton() { return new AndroidButton(); }
    Textbox createTextbox() { return new AndroidTextbox(); }
    Menu createMenu() { return new AndroidMenu(); }
}
```

### Step 5: Use in Client Code
```java
UIFactory factory = new iOSFactory();  // Choose theme
Button button = factory.createButton();
Textbox textbox = factory.createTextbox();
Menu menu = factory.createMenu();

// All are iOS style! ✓ Consistent
```

## ✅ Benefits

1. **Consistency**: Related objects always match (no iOS button with Android textbox)
2. **Easy Theme Switching**: Change one line to switch themes
3. **Encapsulation**: Client doesn't need to know concrete classes
4. **Easy to Extend**: Add new theme without modifying existing code

## ❌ Drawbacks

1. **More Complex** than simple factory
2. **More Classes** to manage
3. **Overkill** if you only have 1-2 product families

## 🎓 When to Use

✅ Use Abstract Factory when:
- You have families of related objects
- You want to ensure consistency within families
- Multiple product families exist
- You want to isolate concrete classes

❌ Don't use if:
- Only one product family
- Product relationships are simple
- Unnecessary complexity

## 🔗 Related Patterns

- **Factory Pattern**: Creates single products
- **Builder Pattern**: Complex object construction
- **Singleton**: Single instance creation

## 🧪 Run the Example

```bash
javac AbstractFactorySimpleExample.java
java AbstractFactorySimpleExample
```

## 📊 What You'll See

```
🍎 ========== iOS APPLICATION ==========
🎨 Rendering iOS Button: Rounded edges, Apple-style
🎨 Rendering iOS Textbox: Rounded corners, iOS style
🎨 Rendering iOS Menu: Apple design language

--- User Interactions ---
🍎 iOS Button clicked (with iOS animation)
🍎 iOS Textbox: Set text to 'Hello World'
🍎 iOS Menu: Sliding from left (iOS navigation style)


🤖 ========== ANDROID APPLICATION ==========
🎨 Rendering Android Button: Sharp edges, Material Design
🎨 Rendering Android Textbox: Material Design style
🎨 Rendering Android Menu: Hamburger menu with Material animation

--- User Interactions ---
🤖 Android Button clicked (with Material ripple effect)
🤖 Android Textbox: Set text to 'Hello World'
🤖 Android Menu: Hamburger menu with Material animation
```

## 🎯 Main Takeaway

**Abstract Factory = "Create a complete family of related objects, not just one"**

Think: You're not just buying a chair (Factory), you're buying a complete furniture set that all matches (Abstract Factory)!
