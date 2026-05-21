package com.code.basic.designpattern.factory;

/**
 * ABSTRACT FACTORY PATTERN - Simple Example
 * 
 * Real-world analogy:
 * Different phone operating systems (iOS, Android) have different UI components
 * but they all follow the same structure.
 * 
 * iOS Theme: Has iOS buttons, iOS textbox, iOS menu
 * Android Theme: Has Android buttons, Android textbox, Android menu
 * 
 * The Abstract Factory ensures that when creating iOS UI,
 * all components are iOS style (not mixed with Android).
 */

// ============ PRODUCT INTERFACES ============

/**
 * Abstract Product 1: Button interface
 */
interface Button {
    void click();
    void render();
}

/**
 * Abstract Product 2: Textbox interface
 */
interface Textbox {
    void setText(String text);
    void render();
}

/**
 * Abstract Product 3: Menu interface
 */
interface Menu {
    void open();
    void render();
}

// ============ CONCRETE PRODUCTS - iOS ============

/**
 * Concrete iOS Button
 */
class iOSButton implements Button {
    @Override
    public void click() {
        System.out.println("🍎 iOS Button clicked (with iOS animation)");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering iOS Button: Rounded edges, Apple-style");
    }
}

/**
 * Concrete iOS Textbox
 */
class iOSTextbox implements Textbox {
    @Override
    public void setText(String text) {
        System.out.println("🍎 iOS Textbox: Set text to '" + text + "'");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering iOS Textbox: Rounded corners, iOS style");
    }
}

/**
 * Concrete iOS Menu
 */
class iOSMenu implements Menu {
    @Override
    public void open() {
        System.out.println("🍎 iOS Menu: Sliding from left (iOS navigation style)");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering iOS Menu: Apple design language");
    }
}

// ============ CONCRETE PRODUCTS - Android ============

/**
 * Concrete Android Button
 */
class AndroidButton implements Button {
    @Override
    public void click() {
        System.out.println("🤖 Android Button clicked (with Material ripple effect)");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering Android Button: Sharp edges, Material Design");
    }
}

/**
 * Concrete Android Textbox
 */
class AndroidTextbox implements Textbox {
    @Override
    public void setText(String text) {
        System.out.println("🤖 Android Textbox: Set text to '" + text + "'");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering Android Textbox: Material Design style");
    }
}

/**
 * Concrete Android Menu
 */
class AndroidMenu implements Menu {
    @Override
    public void open() {
        System.out.println("🤖 Android Menu: Hamburger menu with Material animation");
    }

    @Override
    public void render() {
        System.out.println("🎨 Rendering Android Menu: Material Design language");
    }
}

// ============ ABSTRACT FACTORY ============

/**
 * Abstract Factory Interface
 * Defines which products to create
 */
interface UIFactory {
    Button createButton();
    Textbox createTextbox();
    Menu createMenu();
}

// ============ CONCRETE FACTORIES ============

/**
 * iOS Factory - creates iOS UI components
 */
class iOSFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new iOSButton();
    }

    @Override
    public Textbox createTextbox() {
        return new iOSTextbox();
    }

    @Override
    public Menu createMenu() {
        return new iOSMenu();
    }
}

/**
 * Android Factory - creates Android UI components
 */
class AndroidFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new AndroidButton();
    }

    @Override
    public Textbox createTextbox() {
        return new AndroidTextbox();
    }

    @Override
    public Menu createMenu() {
        return new AndroidMenu();
    }
}

// ============ CLIENT CODE ============

/**
 * Application - doesn't care which theme, just uses Factory
 */
class Application {
    private Button button;
    private Textbox textbox;
    private Menu menu;

    // Constructor takes factory as parameter
    public Application(UIFactory factory) {
        // Create UI components using factory
        button = factory.createButton();
        textbox = factory.createTextbox();
        menu = factory.createMenu();
    }

    public void render() {
        button.render();
        textbox.render();
        menu.render();
    }

    public void interact() {
        System.out.println("\n--- User Interactions ---");
        button.click();
        textbox.setText("Hello World");
        menu.open();
    }
}

// ============ DEMO ============

public class AbstractFactorySimpleExample {

    public static void main(String[] args) {
        System.out.println("====== ABSTRACT FACTORY PATTERN DEMO ======\n");

        // Example 1: Create iOS App
        System.out.println("🍎 ========== iOS APPLICATION ==========");
        UIFactory iOSFactory = new iOSFactory();
        Application iosApp = new Application(iOSFactory);
        iosApp.render();
        iosApp.interact();

        // Example 2: Create Android App
        System.out.println("\n\n🤖 ========== ANDROID APPLICATION ==========");
        UIFactory androidFactory = new AndroidFactory();
        Application androidApp = new Application(androidFactory);
        androidApp.render();
        androidApp.interact();

        // Key Point shown here:
        System.out.println("\n\n✨ KEY POINT:");
        System.out.println("- Same Application code works with both iOS and Android");
        System.out.println("- Each theme gets consistent components from its factory");
        System.out.println("- No mixing of iOS buttons with Android textbox");
        System.out.println("- Easy to add new theme (Windows, macOS, etc.)");
    }
}
