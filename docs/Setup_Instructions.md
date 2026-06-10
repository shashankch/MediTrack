# Setup Instructions

## Install & Configure Java (JDK/JRE)

### Prerequisites

- Operating System: macOS, Windows, or Linux
- Internet connection for downloads

### Step 1: Download JDK

1. Visit the official Oracle JDK download page: [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)
2. Download JDK 17 or later (LTS version recommended).
   - For macOS: Choose the .dmg file.
   - For Windows: Choose the .exe installer.
   - For Linux: Choose the .tar.gz archive.

### Step 2: Install JDK

- **macOS**: Double-click the .dmg file and follow the installer prompts.
- **Windows**: Run the .exe installer and follow the setup wizard.
- **Linux**: Extract the .tar.gz to a directory (e.g., `/usr/local/java`) and set environment variables.

### Step 3: Configure Environment Variables

After installation, set the `JAVA_HOME` and update `PATH`.

- **macOS/Linux**:
  1. Open terminal.
  2. Edit your shell profile (e.g., `~/.bash_profile` or `~/.zshrc`):
     ```
     export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home  # Adjust path as needed
     export PATH=$JAVA_HOME/bin:$PATH
     ```
  3. Reload the profile: `source ~/.zshrc`

- **Windows**:
  1. Open System Properties > Advanced > Environment Variables.
  2. Add `JAVA_HOME` as a new system variable pointing to the JDK installation directory (e.g., `C:\Program Files\Java\jdk-17`).
  3. Edit `PATH` to include `%JAVA_HOME%\bin`.

### Step 4: Verify Installation

Open a terminal/command prompt and run:

```
java -version
javac -version
```

Expected output (example for JDK 17):

```
java version "17.0.1" 2021-10-19 LTS
Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)
javac 17.0.1
```

### Step 5: Compile the Project

Navigate to the project root directory (`MediTrack/`):

```
javac -d out $(find src -name "*.java")
```

**Note:** The `out/` directory is gitignored and will not be committed to the repository.

### Step 6: Run the Application

```
java -cp out com.health.meditrack.Main
```

For loading CSV data, run with arguments:

```
java -cp out com.health.meditrack.Main --loadData
```

### Troubleshooting

- If `java` command not found, ensure `PATH` is set correctly.
- For permission issues on macOS/Linux, use `sudo` if necessary, but prefer user-level installation.
- If using an IDE like IntelliJ or Eclipse, configure the JDK in the project settings.
