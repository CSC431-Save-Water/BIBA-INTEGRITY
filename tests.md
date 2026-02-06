# Bell-LaPadula Security Model Test Cases

## Test Environment Setup

### Users and Their Security Clearances
| User Name       | User ID                                  | Clearance Level |
|-----------------|------------------------------------------|-----------------|
| Valentine Davis | ec187618-c564-425f-98af-281893f97660     | Unclassified    |
| Pauline Pugh    | d807ce56-94e6-42ad-9549-9b8fd5881eb2     | Top Secret      |
| Brandon Lee     | 13dd39e9-45a2-446f-903a-291d02d965d0     | Secret          |
| Robert Yestur   | 4f23df30-59c5-4f37-b6b2-dd03a9c498f9     | Confidential    |

### Objects and Their Classification Levels
| Object Type  | Object ID                                | Classification Level |
|--------------|------------------------------------------|----------------------|
| Mission Spec | e2a117a9-9918-4b26-8e96-7089529e34e5     | Top Secret           |
| Weapon Spec  | 39d40ba2-dcc5-4085-8d2d-ae85882b09f0     | Secret               |
| Mission Spec | 5bcd110b-285f-4ec1-8d0a-ac1137aa10f1     | Confidential         |
| Weapon Spec  | ff257089-4dfe-413c-94ac-b496d0eeefc5     | Unclassified         |

===============================================================================

## Test Case 1: No Read Up (Simple Security Property)

**Bell-LaPadula Rule**: Users cannot read objects at a higher classification level 
than their clearance.

-------------------------------------------------------------------------------
### Test 1.1: Successful Read - Same Level ✓

**User**: Brandon Lee (Secret)
**Object**: Weapon Spec 39d40ba2... (Secret)
**Operation**: READ field "Name"

Enter the User Id you wish to simulate as: 13dd39e9-45a2-446f-903a-291d02d965d0
Enter the Object Id you wish to touch: 39d40ba2-dcc5-4085-8d2d-ae85882b09f0
Enter the operation you wish to preform (READ, WRITE): READ
Weapon Spec:
    Description (class java.lang.String)
    Name (class java.lang.String)
    Classification (class java.lang.String)
Enter the field you wish to preform the operation on (case-sensitive): Name
Miracle

# ← RESULT: Read successful - user clearance matches object classification

-------------------------------------------------------------------------------
### Test 1.2: Successful Read - Lower Level ✓

**User**: Brandon Lee (Secret)
**Object**: Weapon Spec ff257089... (Unclassified)
**Operation**: READ field "Name"

Enter the User Id you wish to simulate as: 13dd39e9-45a2-446f-903a-291d02d965d0
Enter the Object Id you wish to touch: ff257089-4dfe-413c-94ac-b496d0eeefc5
Enter the operation you wish to preform (READ, WRITE): READ
Weapon Spec:
    Description (class java.lang.String)
    Name (class java.lang.String)
    Classification (class java.lang.String)
Enter the field you wish to preform the operation on (case-sensitive): Name
Bombastic Side Eye

# ← RESULT: Read successful - user clearance exceeds object classification

-------------------------------------------------------------------------------
### Test 1.3: Failed Read - Higher Level ✗

**User**: Robert Yestur (Confidential)
**Object**: Mission Spec e2a117a9... (Top Secret)
**Operation**: READ field "Title"

Enter the User Id you wish to simulate as: 4f23df30-59c5-4f37-b6b2-dd03a9c498f9
Enter the Object Id you wish to touch: e2a117a9-9918-4b26-8e96-7089529e34e5
Enter the operation you wish to preform (READ, WRITE): READ
Mission Spec:
    Title (class java.lang.String)
    Content (class java.lang.String)
Enter the field you wish to preform the operation on (case-sensitive): Title

# ← RESULT: Access denied - "No Read Up" violation

java.lang.Exception: Access Denied [Simple Security Property]: User 'Robert 
Yestur' (Confidential) cannot read object 'e2a117a9-9918-4b26-8e96-7089529e34e5' 
(Top Secret). No Read-Up allowed.

===============================================================================

## Test Case 2: No Write Down (Star Property)

**Bell-LaPadula Rule**: Users cannot write to objects at a lower classification 
level than their clearance.

-------------------------------------------------------------------------------
### Test 2.1: Successful Write - Same Level ✓

**User**: Robert Yestur (Confidential)
**Object**: Mission Spec 5bcd110b... (Confidential)
**Operation**: WRITE field "Title" with value "newTitle"

Enter the User Id you wish to simulate as: 4f23df30-59c5-4f37-b6b2-dd03a9c498f9
Enter the Object Id you wish to touch: 5bcd110b-285f-4ec1-8d0a-ac1137aa10f1
Enter the operation you wish to preform (READ, WRITE): WRITE
Mission Spec:
    Title (class java.lang.String)
    Content (class java.lang.String)
Enter the field you wish to preform the operation on (case-sensitive): Title
Enter Value: newTitle
Write Successful.

# ← RESULT: Write successful - user clearance matches object classification

-------------------------------------------------------------------------------
### Test 2.2: Failed Write - Lower Level ✗

**User**: Robert Yestur (Confidential)
**Object**: Weapon Spec ff257089... (Unclassified)
**Operation**: WRITE field "Name" with value "newName"

Enter the User Id you wish to simulate as: 4f23df30-59c5-4f37-b6b2-dd03a9c498f9
Enter the Object Id you wish to touch: ff257089-4dfe-413c-94ac-b496d0eeefc5
Enter the operation you wish to preform (READ, WRITE): WRITE
Weapon Spec:
    Description (class java.lang.String)
    Name (class java.lang.String)
    Classification (class java.lang.String)
Enter the field you wish to preform the operation on (case-sensitive): Name
Enter Value: newName

# ← RESULT: Access denied - "No Write Down" violation

java.lang.Exception: Access Denied [Simple Security Property]: User 'Robert 
Yestur' (Confidential) cannot write to object 'ff257089-4dfe-413c-94ac-
b496d0eeefc5' (Unclassifed). No Write-Down allowed.

===============================================================================

## Test Case 3: Input Validation

**Purpose**: Verify system handles invalid inputs with appropriate error messages 
and retry prompts.

-------------------------------------------------------------------------------
### Test 3.1: Invalid User ID

Enter the User Id you wish to simulate as: 1234-5678
User does not exists (1234-5678) please try again: ec187618-c564-425f-98af-281893f97660

# ← System detected invalid UUID format and prompted for retry

-------------------------------------------------------------------------------
### Test 3.2: Invalid Object ID

Enter the Object Id you wish to touch: abcd-efgh
Model does not exists (abcd-efgh) please try again: e2a117a9-9918-4b26-8e96-7089529e34e5

# ← System detected non-existent object ID and prompted for retry

-------------------------------------------------------------------------------
### Test 3.3: Invalid Operations

Enter the operation you wish to preform (READ, WRITE): DELETE
Invalid operation (DELETE) please try again: REading

# ← System rejected unsupported operation (DELETE)

Invalid operation (READING) please try again: READ

# ← System rejected case-mismatched operation and prompted for retry with 
# correct format

===============================================================================

## Summary

**Test Results**:
- ✓ No Read Up enforcement: 3/3 tests passed
- ✓ No Write Down enforcement: 2/2 tests passed
- ✓ Input validation: 3/3 tests passed

**Total**: 8/8 test cases validated Bell-LaPadula security properties correctly

===============================================================================