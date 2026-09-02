/**
 * Copyright (c) [2022 - Present] Stɑrry Shivɑm
 * Licensed under the Apache License, Version 2.0.
 *
 * AS-KetabYar preserves the upstream attribution while maintaining
 * project-specific links and support information in one place.
 */
package com.starry.myne.helpers

object Constants {
    /** نام دیتابیس فعلی عمداً ثابت مانده تا داده‌های موجود بدون Migration از بین نروند. */
    const val DATABASE_NAME = "myne.db"

    /** نوع MIME استاندارد فایل EPUB. */
    const val EPUB_MIME_TYPE = "application/epub+zip"

    /** مقدار جایگزین برای خطاهای ناشناخته. */
    const val UNKNOWN_ERR = "unknown-error"

    // اطلاعات و لینک‌های پروژه کتاب‌یار.
    const val DEV_EMAIL = "AS.Developers.Support@Gmail.Com"
    const val DEV_GITHUB_URL = "https://github.com/waxew"
    const val DEV_TELEGRAM_URL = "https://github.com/waxew/AS-KetabYar"
    const val GITHUB_REPO = "https://github.com/waxew/AS-KetabYar"
    const val GITHUB_ISSUE = "https://github.com/waxew/AS-KetabYar/issues/new"
    const val WEBSITE = GITHUB_REPO
    const val PRIVACY_POLICY = "https://github.com/waxew/AS-KetabYar/blob/main/legal/PRIVACY-POLICY.md"

    // اعتبار پروژه پایه مطابق مجوز متن‌باز حفظ می‌شود.
    const val PROJECT_CONTRIBUTORS = "https://github.com/Pool-Of-Tears/Myne/graphs/contributors"
    const val TELEGRAM_GROUP = GITHUB_REPO
    const val SUPPORT = GITHUB_REPO
}