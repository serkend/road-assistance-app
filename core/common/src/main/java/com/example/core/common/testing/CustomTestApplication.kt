package com.example.core.common.testing

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class)
interface CustomTestApplication