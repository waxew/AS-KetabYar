# AS-KetabYar | کتاب‌یار

کتاب‌یار یک برنامه اندرویدی برای جستجو، دریافت، مدیریت و مطالعه کتاب‌های الکترونیکی است. پروژه بر پایه پروژه متن‌باز **Myne** توسعه داده می‌شود و در نسخه AS Team رابط فارسی، هویت مستقل، مسیر توسعه مشخص و امکانات تکمیلی کتابخانه شخصی به آن اضافه می‌شود.

## وضعیت فعلی

- Android Native با Kotlin و Jetpack Compose
- حداقل Android 8 / API 26
- جستجو و مرور کتاب‌ها
- دسته‌بندی کتاب‌ها
- کتابخانه شخصی
- Import فایل EPUB
- کتاب‌خوان داخلی EPUB
- نشانک و فهرست فصل‌ها
- تنظیم اندازه متن و فاصله خطوط
- حالت روشن، تیره و AMOLED
- Material You
- Room Database برای اطلاعات محلی
- DataStore برای تنظیمات
- رابط اصلی فارسی
- شناسه انتشار: `com.asteam.ketabyar`
- نام خروجی Release: `AS-KetabYar-v<version>.apk`

## معماری و فناوری‌ها

- Kotlin
- Jetpack Compose / Material 3
- MVVM
- Room
- Hilt
- Coroutines / Flow
- OkHttp
- Kotlin Serialization
- Coil
- Jsoup
- DataStore

## برنامه توسعه AS Team

1. تکمیل فارسی‌سازی و RTL تمام صفحات.
2. طراحی هویت بصری و آیکون اختصاصی کتاب‌یار.
3. اضافه‌کردن Drawer استاندارد AS Team شامل پروفایل، خانه، کتابخانه، تنظیمات، اشتراک‌گذاری، درباره نرم‌افزار، تماس با ما و خروج.
4. اصلاح رفتار Back در تمام مسیرهای Navigation.
5. تکمیل مدیریت کتابخانه: جستجو، مرتب‌سازی، فیلتر، علاقه‌مندی و وضعیت مطالعه.
6. ثبت پیشرفت مطالعه و آخرین صفحه/فصل.
7. توسعه یادداشت و هایلایت برای کتاب‌ها.
8. حفظ داده‌های کاربر هنگام Update و تعریف Migration برای تغییرات آینده دیتابیس.
9. بررسی نسخه جدید برنامه از داخل اپ.
10. آماده‌سازی Release APK، Debug APK، سورس ZIP، checksum و گزارش امضا در مرحله انتشار.

## سیاست حفظ داده

نام دیتابیس فعلی در فاز نخست عمداً تغییر نکرده است. هر تغییر ساختاری در Room باید همراه Migration انجام شود تا کتابخانه، تنظیمات و وضعیت مطالعه کاربر در بروزرسانی‌های بعدی حذف نشود.

## ارتباط

- GitHub: `waxew/AS-KetabYar`
- پشتیبانی: `AS.Developers.Support@Gmail.Com`
- Develop by AS Team Group

## پروژه پایه و مجوز

این پروژه از سورس متن‌باز [Myne](https://github.com/Pool-Of-Tears/Myne) استفاده می‌کند. حقوق مؤلف و مجوز Apache License 2.0 پروژه اصلی حفظ شده‌اند. فایل `LICENSE` و attributionهای موجود در سورس نباید حذف شوند.

Myne original copyright:

`Copyright (c) [2022 - Present] Stɑrry Shivɑm`

Licensed under the Apache License, Version 2.0.