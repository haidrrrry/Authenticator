package org.haidrrrry.auth.utils

import androidx.compose.ui.graphics.Color

object AppColors {
    // Light Theme Colors
    object Light {
        val Primary = Color(0xFF6366F1) // Indigo-500
        val PrimaryVariant = Color(0xFF4F46E5) // Indigo-600
        val PrimaryLight = Color(0xFF8B5CF6) // Violet-500

        val Background = Color(0xFFF8FAFC) // Slate-50
        val BackgroundSecondary = Color(0xFFF1F5F9) // Slate-100
        val Surface = Color(0xFFFFFFFF) // White
        val SurfaceVariant = Color(0xFFF8FAFC) // Slate-50

        val OnSurface = Color(0xFF0F172A) // Slate-900
        val OnSurfaceVariant = Color(0xFF64748B) // Slate-500
        val OnPrimary = Color(0xFFFFFFFF) // White

        val CardBackground = Color(0xFFFFFFFF)
        val CardElevated = Color(0xFFF8FAFC)

        val Success = Color(0xFF10B981) // Emerald-500
        val Warning = Color(0xFFF59E0B) // Amber-500
        val Error = Color(0xFFEF4444) // Red-500

        val TimerGood = Color(0xFF059669) // Emerald-600
        val TimerWarning = Color(0xFFD97706) // Amber-600
        val TimerCritical = Color(0xFFDC2626) // Red-600
    }

    // Dark Theme Colors
    object Dark {
        val Primary = Color(0xFF818CF8) // Indigo-400 (lighter for dark theme)
        val PrimaryVariant = Color(0xFF6366F1) // Indigo-500
        val PrimaryLight = Color(0xFFA78BFA) // Violet-400

        val Background = Color(0xFF0F172A) // Slate-900
        val BackgroundSecondary = Color(0xFF1E293B) // Slate-800
        val Surface = Color(0xFF1E293B) // Slate-800
        val SurfaceVariant = Color(0xFF334155) // Slate-700

        val OnSurface = Color(0xFFF8FAFC) // Slate-50
        val OnSurfaceVariant = Color(0xFF94A3B8) // Slate-400
        val OnPrimary = Color(0xFF1E293B) // Slate-800

        val CardBackground = Color(0xFF1E293B) // Slate-800
        val CardElevated = Color(0xFF334155) // Slate-700

        val Success = Color(0xFF34D399) // Emerald-400
        val Warning = Color(0xFFFBBF24) // Amber-400
        val Error = Color(0xFFF87171) // Red-400

        val TimerGood = Color(0xFF10B981) // Emerald-500
        val TimerWarning = Color(0xFFF59E0B) // Amber-500
        val TimerCritical = Color(0xFFEF4444) // Red-500
    }
}