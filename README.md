# 🐄 Gokula Health — Digital Cattle Health Passport

**MindMatrix VTU Internship Program | Project Title: 98**

> An Android app that helps small-scale dairy farmers manage the "Life Cycle" of their cows — tracking milk yield trends, vaccination schedules, and breeding cycles.

---

## 📱 Features

| Feature | Description |
|---------|-------------|
| 🐄 **Cattle Profile** | Register each cow with photo & unique Ear Tag ID |
| 🥛 **Milk Diary** | Daily morning + evening yield entry, auto monthly average |
| 📊 **Yield Graph** | MPAndroidChart bar chart showing 30-day milk production |
| 💉 **Vaccination Alerts** | Schedule FMD & other vaccines with AlarmManager reminders |
| 📴 **Offline First** | Room DB — works without internet |
| 🔔 **Reboot-safe** | BootReceiver re-schedules alarms after device restart |

---

## 🏗️ Tech Stack

- **Language**: Kotlin
- **Database**: Room (SQLite)
- **Charts**: MPAndroidChart
- **Architecture**: MVVM (ViewModel + LiveData + Repository)
- **Notifications**: AlarmManager + BroadcastReceiver
- **Image Loading**: Glide

---

## 🚀 How to Run

1. Clone this repo
2. Open in **Android Studio** (Hedgehog or later)
3. Wait for Gradle sync (downloads MPAndroidChart from JitPack)
4. Add `res/drawable/ic_cow.xml`, `ic_syringe.xml`, `ic_milk.xml` via New → Vector Asset
5. Run on device or emulator (min API 24)

---

## 📂 Project Structure

```
app/src/main/java/com/example/gokulahealth/
├── data/           → Room entities + DAOs + Database
├── repository/     → CattleRepository
├── viewmodel/      → CattleViewModel
├── notification/   → AlarmManager + Receivers
└── ui/
    ├── home/       → Dashboard Fragment
    ├── cattle/     → List, Detail, Add Cattle
    ├── milk/       → Add Milk Entry
    ├── vaccination/→ Add Vaccination
    └── alerts/     → All pending vaccinations
```

---

## 🎯 Impact Goals

- **White Revolution 2.0** — Data-driven dairy productivity
- **Farmer Income** — 100% vaccination coverage, no missed Heat Cycles
- **Rural Digitization** — Precision technology for animal husbandry
