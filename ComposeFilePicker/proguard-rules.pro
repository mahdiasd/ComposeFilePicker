###########################
# ProGuard Rules for Jetpack Compose
###########################

# Keep all classes and interfaces in the androidx.compose packages
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Keep the @Composable annotation and other annotations used by Compose
-keepattributes *Annotation*

# Keep the Composable methods (methods annotated with @Composable)
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Keep the metadata annotations required for Compose's reflection
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    *;
}

###########################
# ProGuard Rules for Kotlin
###########################

# Keep Kotlin metadata annotations to prevent issues with reflection
-keepattributes KotlinMetadata

# Keep data classes and their constructors
-keepclassmembers class * {
    public <init>(...);
}

# Keep all extension functions (functions with @kotlin.jvm.JvmName)
-keepclassmembers class * {
    @kotlin.jvm.JvmName *;
}

###########################
# ProGuard Rules for Coil
###########################

# Keep classes and interfaces from Coil
-keep class coil.** { *; }
-keep interface coil.** { *; }

# Don't warn about missing classes in Coil (usually safe to ignore)
-dontwarn coil.**

# Keep Coil's Extension functions and methods used via reflection
-keepclassmembers class coil.** {
    *;
}

# Keep Lifecycle methods used by Coil
-keepclassmembers class * implements androidx.lifecycle.DefaultLifecycleObserver {
    public void onCreate(androidx.lifecycle.LifecycleOwner);
    public void onStart(androidx.lifecycle.LifecycleOwner);
    public void onResume(androidx.lifecycle.LifecycleOwner);
    public void onPause(androidx.lifecycle.LifecycleOwner);
    public void onStop(androidx.lifecycle.LifecycleOwner);
    public void onDestroy(androidx.lifecycle.LifecycleOwner);
}

###########################
# ProGuard Rules for kotlinx.collections.immutable
###########################

# Keep classes and interfaces from kotlinx.collections.immutable
-keep class kotlinx.collections.immutable.** { *; }
-keep interface kotlinx.collections.immutable.** { *; }

# Don't warn about missing classes in kotlinx.collections.immutable
-dontwarn kotlinx.collections.immutable.**

###########################
# ProGuard Rules for AndroidX Lifecycle and ViewModel
###########################

# Keep all ViewModel subclasses and their constructors
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# Keep classes and interfaces from androidx.lifecycle
-keep class androidx.lifecycle.** { *; }
-keep interface androidx.lifecycle.** { *; }

# Don't warn about missing classes in androidx.lifecycle
-dontwarn androidx.lifecycle.**

###########################
# General ProGuard Rules for AndroidX Libraries
###########################

# Keep all classes and interfaces in the androidx packages
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Don't warn about missing classes in androidx packages
-dontwarn androidx.**

###########################
# Keep Your Library Classes
###########################

# Replace 'github.mahdiasd.composefilepicker' with your actual package name if different
-keep class github.mahdiasd.composefilepicker.** { *; }

# Keep all public and protected class members (methods and fields)
-keepclassmembers class * {
    public protected *;
}

###########################
# General Rules for Reflection and Serialization
###########################

# Keep classes that might be accessed via reflection
-keepclassmembers class * {
    @java.lang.SuppressWarnings *;
}

# Keep enums and their values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn java.lang.invoke.StringConcatFactory