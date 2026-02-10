# Biba Integrity Model Test Cases

## Test Environment Setup

### Users and Their Integrity Levels

| User Name | User ID | Integrity Level |
|-----------|---------|-----------------|
| Pauline Pugha | 3ca7b66-41ea-4183-8585-563e65581ddb | Top Secret |
| Brandon Lee | 6618e6ea-69b3-4daa-9353-975342d6fae6 | Secret |
| Robert Yestur | 90ab417b-e85a-48d7-b183-532c4459cb70 | Confidential |
| Valentine Davis | ce0164a1-f097-4582-af21-b92909485af5 | Unclassified |

### Objects and Their Integrity Levels

| Object Type | Object ID | Integrity Level |
|-------------|-----------|-----------------|
| Mission Spec | 56c56f2e-fd34-497a-9de5-5ce428542622 | Top Secret |
| Weapon Spec | 8e2d1aa9-17e6-4b17-ba87-38c6e6d5562f | Secret |
| Mission Spec | ebbe1830-1ee4-46ff-9819-14bee69340c5 | Confidential |
| Weapon Spec | a727ad5a-e986-437d-ac1e-e4a0d19bc3da | Unclassified |

## Test Case 1: Simple Integrity Property (No Read Down)

**Biba Rule:** To prevent "contamination" from less reliable sources, users cannot read data from a lower integrity level than their own.

### Test 1.1: Failed Read - Lower Level ✗

- **User:** Pauline Pugh (Top Secret)
- **Object:** Weapon Spec a727ad5a... (Unclassified)
- **Operation:** READ field "Name"
- **Result:**
  ```
  java.lang.Exception: Access Denied [Simple Integrity Property]: 
  User 'Pauline Pugh' (Top Secret) cannot read object 'a727ad5a...' (Unclassified). 
  No Read-Down allowed.
  ```

### Test 1.2: Successful Read - Higher Level ✓

- **User:** Robert Yestur (Confidential)
- **Object:** Mission Spec 56c56f2e... (Top Secret)
- **Operation:** READ field "Title"
- **Result:**
  ```
  Output: Ash
  ```
  
*Success: Integrity is preserved because a lower-level user reading higher-level data does not corrupt the higher-level data.*

## Test Case 2: Star (*) Integrity Property (No Write Up)

**Biba Rule:** To prevent "polluting" high-integrity data, users cannot write to objects at a higher integrity level than their own.

### Test 2.1: Failed Write - Higher Level ✗

- **User:** Valentine Davis (Unclassified)
- **Object:** Weapon Spec 8e2d1aa9... (Secret)
- **Operation:** WRITE field "Name" with "newName"
- **Result:**
  ```
  java.lang.Exception: Access Denied [*Integrity-Property]: 
  User 'Valentine Davis' (Unclassified) cannot write to object '8e2d1aa9...' (Secret). 
  No Write-Up allowed.
  ```

### Test 2.2: Successful Write - Lower Level ✓

- **User:** Brandon Lee (Secret)
- **Object:** Mission Spec ebbe1830... (Confidential)
- **Operation:** WRITE field "Content" with "This is a test"
- **Result:**
  ```
  Write Successful.
  ```

*Success: A high-integrity user is permitted to write to a lower-integrity object.*

## Summary of Results

| Property | Rule | Result |
|----------|------|--------|
| Simple Integrity | No Read Down | PASSED (Prevents contamination) |
| Star (*) Integrity | No Write Up | PASSED (Prevents pollution) |
