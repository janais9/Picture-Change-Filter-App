# 🖼️ Picture Change App (Java Swing)

A Java Swing desktop application that demonstrates **image processing**, **multithreading**, and **UI interaction**.  
The app allows users to apply color filters to images either step-by-step or through continuous animation, with performance monitoring.

---

## ✨ Features

- 📂 Load an image from your device
- 🪜 **Step Mode**  
  Apply a color filter once on the first image using a background thread
- 🔄 **Animation Mode**  
  Continuously apply a color filter on the second image using a separate thread
- ⏱️ **Performance Warning**  
  Displays a warning dialog if image processing exceeds a defined time limit
- ♻️ **Reset Button**  
  Restore both images to their original state
- 🎨 Simple and clean Swing-based UI

---

## 🧠 Concepts Demonstrated

- Java Swing GUI
- Multithreading (`Thread`, `SwingUtilities.invokeLater`)
- Image processing using `BufferedImage`
- Thread-safe UI updates
- Performance monitoring
- Event-driven programming

---

## 🖥️ Application Preview
- **When execute**:
![App Screenshot](https://raw.githubusercontent.com/janais9/Picture-Change-Filter-App/main/Screenshot%202025-12-15%20090151.png)

- **Left Panel**: Step-based image filtering
- **Right Panel**: Animated image filtering
  ![App Screenshot](https://raw.githubusercontent.com/janais9/Picture-Change-Filter-App/main/Screenshot%202025-12-15%20090250.png)

  
- **Buttons**:
  - `Step` → Applies filter once:
  - `Start / Stop` → Toggles animation
  - `Reset` → Restores original image
 
  ![App Screenshot](https://raw.githubusercontent.com/janais9/Picture-Change-Filter-App/main/Screenshot%202025-12-15%20090301.png)


---
## ⚠️ Performance Alert
If image processing takes longer than **500 ms**, a warning dialog will appear:
> "We apologize, image processing will take some time."

This helps demonstrate responsiveness and user feedback in UI applications.


---
 ## 🚀 Try It!!

1. Make sure you have **Java JDK 8 or higher**
2. Clone the repository:
   ```bash
   git clone https://github.com/your-username/PictureChangeApp.git
