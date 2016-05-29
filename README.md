<h3>Hearthstone Arena Card Selector</h3>
<strong>May 2016</strong><br><br>
<strong>About:</strong><br>
This application assigns scores to cards based on entries given a formatted database. The database will feature cards and their previous win history. This algorithm works by assigning points to certain pairings and how they performed in previous arenas.<br><br>
<strong>Quick Start Guide:</strong><br>
1. Add files to a web server which supports PHP.<br>
2. Create an index.html which "requires" Library.php. Create a new Library object and start calling functions from it.<br><br>
<strong>Other Notes:</strong><br>
- This version is not secure. Things such as XSS (cross-site scripting) attacks are still possible.<br>
- Performance is an issue. The code runs in O(n^2) time where n is the number of database entries. PHP is also notoriously slow. Any suggestions and fixes are welcomed.
