package br.ufrn.revysa

import androidx.window.core.layout.WindowSizeClass

internal fun useNavigationRail(window: WindowSizeClass): Boolean =
    window.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
