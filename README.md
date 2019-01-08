3494\_2019\_repo
---

[![Build Status](https://travis-ci.com/BHSSFRC/3494_2019_repo.svg?branch=master)](https://travis-ci.com/BHSSFRC/3494_2019_repo)  
:coffee: :robot:

FRC Team 3494's 2019 source code repository.

### Using this code with IntelliJ IDEA
#### Initial configuration
Initial config after cloning is quite simple. Simply `cd` into the
source directory and run `./gradlew idea` (on *NIX) or `gradlew.bat idea`
(on Windows.)

#### Updating vendor libraries
If vendor libraries have been updated or changed (read: any changes made to the `vendordeps` directory),
IDEA has to be made aware of these changes. The process is sadly convoluted, but
can be described with a set of one-after-the-other steps.
1. Close IntelliJ. This is actually an important step - you'll get into a bad state if you don't.
2. Delete all of the IntelliJ files. By default, these are:
    * `3494_2019_repo.iml`
    * `3494_2019_repo.ipr`
    * `3494_2019_repo.iws`
    * `3494_2019_repo.main.iml`
    * `3494_2019_repo.test.iml`
3. Re-run the `idea` Gradle task.
4. Open IntelliJ again. You should be prompted to import the Gradle project again, and if all goes well, should have no
issues after indexing finishes.

~~Blame WPI.~~