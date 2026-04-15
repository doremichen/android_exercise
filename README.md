# Android Exercise Project
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://www.android.com/)
[![Build](https://img.shields.io/badge/Build-Gradle-blue.svg)](https://gradle.org/)

---

## 📖 專案簡介 | Introduction

**Android Exercise Project** 收錄多個 Android 練習專案與 Demo App，  
涵蓋從基礎 UI 實作、系統行為理解，到硬體與通訊層級的應用實例。

This repository contains multiple Android exercise projects and demo apps,  
covering topics from basic UI implementation to system-level and hardware-related applications.

---

## 📱 專案列表 | Project List

### 🧩 核心 UI 與架構練習  
### Core UI & Architecture Exercises

- **FragmentDemo**  
  - Fragment 管理、ViewPager2 與 RecyclerView 綜合應用  
  - 導入 Material 3 設計規範  
  - Fragment management, ViewPager2 & RecyclerView integration  
  - Material 3 design applied

- **AndroidListDemo**  
  - 各式 ListView 清單佈局與效能優化練習  
  - ListView layouts and performance optimization examples

- **AppStartUp**  
  - Android App 啟動流程分析與優化  
  - Android application startup process and optimization techniques

- **NavigationExample**  
  - Jetpack Navigation 元件使用示範  
  - Demonstration of Jetpack Navigation Component

---

### ⚙️ 系統與硬體相關  
### System & Hardware Related

- **AutoWakeUp (System App)** ⚠️  
  - 系統級應用，需系統簽署（System Signature）  
  - 實作設備自動喚醒機制  
  - System-level app requiring system signature  
  - Demonstrates device auto wake-up behavior

- **AutoBootService**  
  - 實作開機自動啟動服務（BOOT_COMPLETED）  
  - Auto-start service triggered by BOOT_COMPLETED

- **BatteryInfo**  
  - 顯示電池電量、健康度、溫度等狀態  
  - Display battery level, health, temperature, and status

- **CameraSample**  
  - 相機 API 呼叫與影像處理練習  
  - Camera API usage and image processing examples

- **GetSysInfo**  
  - 讀取系統參數與硬體規格  
  - Retrieve system and hardware information

---

### 🔌 通訊與監測  
### Communication & Monitoring

- **USBToDeviceComm / UsbTest**  
  - USB 通訊協議實作與外接設備測試  
  - USB communication protocol and external device testing

- **UartSample**  
  - UART 串列通訊實作  
  - UART serial communication example

- **TimerTest**  
  - Timer 啟動／取消行為效能測試  
  - 整合電池資訊監控  
  - Timer performance testing with battery monitoring

- **AlarmWakeUpDemo**  
  - 使用 AlarmManager 實作定時喚醒與提醒  
  - Wake-up and reminder implementation using AlarmManager

- **WidgetDemo**  
  - Android App Widget（桌面小工具）開發範例  
  - Android App Widget development example

---

## 🛠️ 開發環境 | Development Environment

- **IDE**：Android Studio（Gradle 已完成遷移）  
- **Material Design**：部分專案導入 Material 3 與 View Binding  
- **Build System**：Gradle  

---

## ⚠️ 注意事項 | Notes

- **System Signature Required**  
  - `AutoWakeUp` 為系統級應用，需使用系統簽署金鑰才能編譯與安裝  
  - AutoWakeUp requires system signature to build and run

- **Hardware Dependency**  
  - UART、USB 等通訊專案需對應硬體環境  
  - Some communication projects require specific hardware support

---

## 📜 授權協議 | License

This project is licensed under the **MIT License**.  
本專案採用 **MIT License** 授權。

請參閱 `LICENSE` 檔案以取得完整授權內容。  
See the `LICENSE` file for full license text.

---

## 💡 備註 | Notes

- 專案依「功能類型」分類，方便快速理解與學習  
- System App 類型專案已特別標註，避免誤用  
- 適合作為 Android 系統行為、App 架構與底層互動的學習範例  

Well-structured by functionality for better readability and learning.  
System-level projects are clearly marked to avoid confusion.  
Suitable for learning Android architecture, system behavior, and hardware interaction.