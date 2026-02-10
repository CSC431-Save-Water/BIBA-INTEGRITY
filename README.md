# Biba Integrity Model Implementation in Java

## Team: Save Water
- Jacob Pugh
- Brandon Magana
- Eli Manning

## Project Origin
This project is a **fork of our Bell-LaPadula (BLP) Model implementation**, adapted to demonstrate the Biba Integrity Model. While Bell-LaPadula focuses on confidentiality (preventing unauthorized disclosure), Biba focuses on integrity (preventing unauthorized modification and contamination of data).

## Assignment Overview
This project implements the Biba Integrity Model in Java, demonstrating integrity-focused access control through the enforcement of two fundamental integrity properties:
- **Simple Integrity Property** ("no read down"): Users cannot read objects with a lower integrity level to prevent contamination from less reliable sources
- **Star Integrity Property (★-property)** ("no write up"): Users cannot write to objects with a higher integrity level to prevent pollution of high-integrity data

## Key Differences from Bell-LaPadula
| Aspect | Bell-LaPadula | Biba |
|--------|---------------|------|
| **Focus** | Confidentiality | Integrity |
| **Read Rule** | No read up (prevent disclosure) | No read down (prevent contamination) |
| **Write Rule** | No write down (prevent disclosure) | No write up (prevent pollution) |
| **Goal** | Protect sensitive information | Protect data reliability and trustworthiness |

## Project Structure
```
src/
├── Main.java
└── models/
    ├── BaseModel.java
    ├── MissionSpecModel.java
    ├── WeaponsSpecModel.java
    ├── User.java
    └── SecurityLevel.java
```

## System Components

### Integrity Levels
The system implements four hierarchical integrity levels (lowest to highest):
1. **Unclassified** (Level 1) - Lowest integrity/reliability
2. **Confidential** (Level 2)
3. **Secret** (Level 3)
4. **Top Secret** (Level 4) - Highest integrity/reliability

*Note: While the level names remain the same as BLP for code reuse, they now represent integrity/trustworthiness rather than confidentiality.*

### Classes Description

#### `SecurityLevel.java`
- Enumeration defining the four integrity classification levels
- Each level has a name and numerical integrity level for comparison

#### `User.java`
- Represents system users with integrity clearances
- Contains: first name, last name, unique UUID, and integrity level

#### `BaseModel.java`
- Abstract base class for all secure objects
- Implements Biba access control logic
- Contains:
  - Generic Field system for type-safe dynamic attributes
  - `get()` method enforcing "no read down" rule
  - `set()` method enforcing "no write up" rule
  - Integrity level, UUID, and timestamps

#### `MissionSpecModel.java`
- Extends BaseModel to represent mission specification documents
- Fields: Title, Content

#### `WeaponsSpecModel.java`
- Extends BaseModel to represent weapons specification documents
- Fields: Name, Description, Classification

#### `Main.java`
- Interactive simulation driver
- Manages collections of users and objects
- Provides command-line interface for testing Biba rules

## Pre-configured Test Data

### Users
| Name | Integrity Level | UUID (generated at runtime) |
|------|----------------|----------------------------|
| Valentine Davis | Unclassified | Auto-generated |
| Robert Yestur | Confidential | Auto-generated |
| Brandon Lee | Secret | Auto-generated |
| Pauline Pugh | Top Secret | Auto-generated |

### Objects
| Object | Type | Integrity Level | UUID (generated at runtime) |
|--------|------|----------------|----------------------------|
| Ash | Mission Spec | Top Secret | Auto-generated |
| Woods | Mission Spec | Confidential | Auto-generated |
| Miracle | Weapon Spec | Secret | Auto-generated |
| Bombastic Side Eye | Weapon Spec | Unclassified | Auto-generated |

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line access (Terminal, Command Prompt, or PowerShell)

### Compilation Steps

1. **Navigate to the project directory:**
   ```bash
   cd path/to/project
   ```

2. **Compile all Java files:**
   ```bash
   javac -d bin src/Main.java src/models/*.java
   ```
   
   Or, if not using a bin directory:
   ```bash
   javac src/Main.java src/models/*.java
   ```

3. **Run the program:**
   ```bash
   java -cp bin Main
   ```
   
   Or, if compiled without bin directory:
   ```bash
   cd src
   java Main
   ```

### Alternative: Using an IDE
1. Import the project into your IDE (Eclipse, IntelliJ IDEA, NetBeans, VS Code)
2. Ensure the source folder is properly configured
3. Run `Main.java`

## Usage Instructions

When the program starts, it will:
1. Display all available users with their UUIDs and integrity levels
2. Display all available objects with their UUIDs and integrity levels

### Interactive Simulation

The program will prompt you for the following inputs in sequence:

1. **User ID**: Enter the UUID of the user you wish to simulate
   - Copy/paste a UUID from the displayed user list

2. **Object ID**: Enter the UUID of the object you wish to access
   - Copy/paste a UUID from the displayed object list

3. **Operation**: Enter either `READ` or `WRITE`
   - Case-insensitive

4. **Field**: Select which field of the object to access
   - Available fields are displayed for the selected object
   - Field names are case-sensitive (e.g., "Title", "Name")

5. **Value** (for WRITE operations only): Enter the value to write

### Example Interactions

#### Example 1: Successful Read (Lower Reads Higher)
```
Enter the User Id you wish to simulate as: <Robert Yestur UUID>
Enter the Object Id you wish to touch: <Ash (Top Secret) UUID>
Enter the operation you wish to preform (READ, WRITE): READ
Enter the field you wish to preform the operation on (case-sensitive): Title

Output: Ash
✓ Success: Lower integrity user can read higher integrity data without corrupting it
```

#### Example 2: Failed Read (Higher Attempts to Read Lower)
```
Enter the User Id you wish to simulate as: <Pauline Pugh UUID>
Enter the Object Id you wish to touch: <Bombastic Side Eye (Unclassified) UUID>
Enter the operation you wish to preform (READ, WRITE): READ
Enter the field you wish to preform the operation on (case-sensitive): Name

Exception: Access Denied [Simple Integrity Property]
✗ Blocked: Prevents contamination from less reliable sources
```

#### Example 3: Successful Write (Higher Writes to Lower)
```
Enter the User Id you wish to simulate as: <Brandon Lee (Secret) UUID>
Enter the Object Id you wish to touch: <Woods (Confidential) UUID>
Enter the operation you wish to preform (READ, WRITE): WRITE
Enter the field you wish to preform the operation on (case-sensitive): Content
Enter the value for the field: Updated mission details

Write Successful.
✓ Success: High integrity user can write to lower integrity objects
```

#### Example 4: Failed Write (Lower Attempts to Write Higher)
```
Enter the User Id you wish to simulate as: <Valentine Davis (Unclassified) UUID>
Enter the Object Id you wish to touch: <Miracle (Secret) UUID>
Enter the operation you wish to preform (READ, WRITE): WRITE
Enter the field you wish to preform the operation on (case-sensitive): Name
Enter the value for the field: Modified Name

Exception: Access Denied [*Integrity-Property]
✗ Blocked: Prevents pollution of high-integrity data
```

## Biba Rules Implementation

### Simple Integrity Property (No Read Down)
Implemented in `BaseModel.hasReadAccess()`:
```java
return currentUser.getUserSecurityLevel().getClearanceLevel() 
       <= this.securityLevel.getClearanceLevel();
```
- Users cannot read objects with lower integrity levels
- Prevents contamination from less trustworthy sources
- **Inverted from BLP**: Higher-clearance users are *blocked* from reading lower-level data

### Star Integrity Property (No Write Up)
Implemented in `BaseModel.hasWriteAccess()`:
```java
return currentUser.getUserSecurityLevel().getClearanceLevel() 
       >= this.securityLevel.getClearanceLevel();
```
- Users cannot write to objects with higher integrity levels
- Prevents pollution of highly trusted data by less trusted sources
- **Inverted from BLP**: Lower-clearance users are *blocked* from writing to higher-level data

## Understanding Biba vs Bell-LaPadula

### Information Flow Direction
- **Bell-LaPadula**: Prevents information flow from HIGH confidentiality to LOW confidentiality
  - Protects secrets from being leaked downward
  
- **Biba**: Prevents information flow from LOW integrity to HIGH integrity
  - Protects trusted data from being corrupted by untrusted sources

### Real-World Analogy
- **BLP**: Military classification - generals can't share secrets with privates
- **Biba**: Software supply chain - untrusted code can't modify critical system files

## Key Features

### Type-Safe Field System
- Generic `Field<T>` class ensures type safety for object attributes
- Dynamic field mapping with compile-time type checking
- Null-safe casting mechanism

### UUID-Based Identification
- All users and objects identified by unique UUIDs
- Prevents conflicts and ensures uniqueness

### Timestamp Tracking
- Objects track creation time and last modification time
- Foundation for audit logging capabilities

### Error Handling
- Invalid UUID detection with user-friendly prompts
- Invalid operation validation
- Invalid field name handling
- Comprehensive access denial messages explaining Biba violations

## Test Cases Summary

| Property | Rule | Result |
|----------|------|--------|
| Simple Integrity | No Read Down | PASSED (Prevents contamination) |
| Star (*) Integrity | No Write Up | PASSED (Prevents pollution) |

See `biba-integrity-model-test-cases.md` for detailed test scenarios.

## Limitations and Future Enhancements

### Current Limitations
- Integrity levels are fixed at object/user creation
- No persistence (data lost when program exits)
- No audit logging of access attempts
- No support for dynamic integrity level adjustments

### Potential Enhancements
- Implement invocation property (restricting what subjects of lower integrity can invoke)
- Add hybrid BLP + Biba model for both confidentiality and integrity
- Implement audit trail for compliance tracking
- Add persistence layer for data storage

## Dependencies
- Java Standard Library only
- No external dependencies required

## Academic Context
This implementation demonstrates the Biba Integrity Model's dual properties and their inverse relationship to the Bell-LaPadula confidentiality model. It serves as an educational tool for understanding mandatory access control (MAC) systems focused on data integrity rather than confidentiality.
