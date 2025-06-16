# 🔐 Authenticator

A modern, cross-platform Two-Factor Authentication (2FA) app built with Compose Multiplatform. Generate secure TOTP codes for your accounts across Android, iOS, and Desktop platforms.

## ✨ Features

- **🔒 Secure TOTP Generation** - Generate time-based one-time passwords following RFC 6238
- **🎨 Modern UI/UX** - Beautiful, animated interface with Material 3 design
- **🌙 Theme Support** - Light and dark themes with smooth transitions
- **📱 Multiple View Modes** - Switch between list and grid layouts
- **🔍 Smart Search** - Quickly find your accounts with real-time search
- **⏰ Visual Timer** - Color-coded progress indicators showing code validity
- **🎭 Smooth Animations** - Fluid transitions and micro-interactions
- **🔐 Privacy-First** - All data stored locally, no cloud sync
- **📋 Easy Code Copy** - Tap to copy codes to clipboard
- **🎯 Cross-Platform** - Single codebase for Android, iOS, and Desktop

## 🚀 Platforms Supported

- **Android** (API 24+)
- **iOS** (iOS 13+)
- **Desktop** (Windows, macOS, Linux)

## 📱 Screenshots

*Coming soon - Screenshots will be added to showcase the beautiful UI across different platforms*

## 🛠️ Tech Stack

- **Compose Multiplatform** - UI framework for cross-platform development
- **Kotlin** - Primary programming language
- **Material 3** - Modern design system
- **Koin** - Dependency injection
- **Coroutines** - Asynchronous programming
- **TOTP Algorithm** - RFC 6238 compliant implementation

## 🏗️ Architecture

The app follows modern Android architecture patterns:

- **MVVM Pattern** - Clear separation of concerns
- **Repository Pattern** - Data layer abstraction
- **Dependency Injection** - Koin for DI container
- **State Management** - Compose state management with ViewModels
- **Clean Architecture** - Organized into layers for maintainability

## 🔧 Setup & Installation

### Prerequisites
- Android Studio or IntelliJ IDEA
- Kotlin Multiplatform Mobile plugin
- JDK 11 or higher

### Clone the Repository
```bash
git clone https://github.com/haidrrrry/Authenticator.git
cd Authenticator
```

### Build for Different Platforms

#### Android
```bash
./gradlew assembleDebug
```

#### iOS
```bash
./gradlew iosX64Test
```

#### Desktop
```bash
./gradlew run
```

## 🎨 Design Highlights

- **Adaptive Theming** - Seamless light/dark mode transitions
- **Smooth Animations** - Spring-based animations for natural feel
- **Color-Coded Timers** - Visual indicators for code expiration
- **Glassmorphism Effects** - Modern visual effects with gradients
- **Responsive Layout** - Adapts beautifully to different screen sizes
- **Accessibility First** - Proper contrast ratios and semantic markup

## 🔐 Security Features

- **Local Storage Only** - No data leaves your device
- **RFC 6238 Compliant** - Standard TOTP implementation
- **Secure Key Management** - Proper handling of secret keys
- **No Network Requests** - Completely offline operation
- **Memory Safety** - Secure cleanup of sensitive data

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Guidelines
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Haider Ali Khan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👤 Author

**Haider Ali Khan**
- Email: [khanhaiderali393@gmail.com](mailto:khanhaiderali393@gmail.com)
- GitHub: [@haidrrrry](https://github.com/haidrrrry)

## 🙏 Acknowledgments

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for the amazing cross-platform framework
- [Material 3](https://m3.material.io/) for the beautiful design system
- [RFC 6238](https://tools.ietf.org/html/rfc6238) for the TOTP specification
- All contributors and users of this project

## 📈 Roadmap

- [ ] Import/Export functionality
- [ ] Backup and restore options
- [ ] Biometric authentication
- [ ] Apple Watch support
- [ ] Wear OS support
- [ ] Custom icons for services
- [ ] Bulk operations
- [ ] Advanced search filters
 

⭐ If you found this project helpful, please give it a star on GitHub!

**Made with ❤️ using Compose Multiplatform**
