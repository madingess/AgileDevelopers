# AgileDevelopers



**Members:**

Dingess, Michael A <madingess@uky.edu>

Poff, Dustin A <dustinpoff@uky.edu>

Al Nemri, Osama S <osama.nemri@uky.edu>



**Project 3 Description:** 

Make Jenkins report whether there are newly passing tests or newly failing tests based on historical information. On every run, Jenkins will only report on all the tests that pass and/or those that fail. The goal of this project is to use information from previous runs so that Jenkins can also report on which tests were passing before, but are now failing (or vice-versa).



**Compilation and Installation**

To compile our code, we pulled the repository to one of our virtual machines and ran 'mvn clean install'. This generated a .hpi file for our plugin in the /target/ directory which we then uploaded to Jenkins via the Advanced tab in the Manage Plugins section of Jenkins' Configuration. To make the plugin run for your desired project, simply go into the configuration of the chosen project and select the plugin from the Post Steps drop-down menu.



**Jenkins Output**

After the plugin is uploaded to Jenkins and configured for your project, it will run after all successful builds (stable or unstable) and after all tests have completed. The plugin will then output one line per test indicating whether the test passed or failed and how this status compares to the status of the test in the previous build.



**Remarks**

Our github is showing only 10 commits at the time of this writing. Between "First Commit" and "Committing Overhaul" there used to be around 40 commits by our team members; however these do not appear now. The reason this occurred is beyond me, but I think it has something to do with the fact that I initialized a new local repository on my virtual machine and forced a push to this repository after we completed the plugin overhaul. We did an overhaul because our repository was missing many necessary files for plugin generation. This was because we started the project from scratch as opposed to using a sample plugin and editing necessary files (which is what we did for the overhaul). I suspect this is the reason the commits do not show up. We hope the current state of our commit log is acceptable given that the commits were reviewed during iterations 1-3.
