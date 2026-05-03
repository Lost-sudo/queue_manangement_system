# User Story Document for Queue Management System

## Actor 1: Student / Visitor (End User)

### US-01: Join a queue for a specific service
As a student,
I want to select an office and a service from the school’s website, then click a button to join the queue,
so that I can get in line without physically waiting in a crowded area.

Acceptance Criteria:

    The website displays a list of available offices (Registrar, Cashier, Guidance, etc.).

    After selecting an office, the page shows the services offered by that office.

    The student clicks “Join Queue” and receives:

        A unique ticket number (e.g., R-105, C-032)

        The current position in the queue

        An estimated waiting time (e.g., “approx. 18 minutes”)

    The ticket is added to the backend queue instantly, and the public display reflects it within 5 seconds.

    If the student’s browser supports it, a notification permission prompt appears.

### US-02: See my ticket status and current serving progress
As a student,
I want to view a page that shows my ticket number, remaining wait time, and the ticket currently being served,
so that I know when it’s my turn, even if I close the browser and reopen it later.

Acceptance Criteria:

    A “Track My Ticket” page allows entering a ticket number.

    The page displays:

        Ticket status (Waiting / Serving / Completed)

        “Now Serving” ticket and counter

        Number of people ahead in the same queue

    Status and remaining wait time update in real time without a full page refresh (using WebSocket or automatic polling every 10 seconds).

    If the ticket is already completed, the page shows “Your turn is finished.”

### US-03: Receive a browser notification when my turn is near
As a student,
I want to get a notification in my web browser when there is only 1 person ahead of me,
so that I can stop browsing and prepare to go to the counter.

Acceptance Criteria:

    After joining, the page asks for notification permission.

    When the student’s queue position becomes “next” (or one more before), a desktop/browser notification appears: “Your turn is almost here. Ticket R‑105, Counter 2.”

    Notifications are only sent if the user has granted permission and the tab is open (or a service worker handles it if closed).

## Actor 2: Counter Staff

US-04: Log in to the staff dashboard
As a cashier or registrar staff,
I want to log in with my credentials,
so that I can only manage the queue assigned to my counter.

Acceptance Criteria:

    Login form accepts username and password.

    After successful login, the staff sees only the queues/services linked to their assigned counter.

    Invalid credentials show an error message without revealing which field is wrong (security).

### US-05: Call the next student in line
As a counter staff,
I want to click a “Call Next” button,
so that the next waiting ticket moves to “Serving” status and the public display and student page update instantly.

Acceptance Criteria:

    When “Call Next” is clicked, the system picks the earliest waiting ticket in the staff’s queue (FIFO).

    The status of that ticket changes to “Serving,” the serving time is recorded, and the ticket is removed from the waiting list.

    The public display and student track pages are updated within 2 seconds (real-time broadcast).

    If no tickets are waiting, the system shows “No tickets in queue” and does nothing.

    Button can be pressed multiple times; each call serves the next waiting ticket.

### US-06: Mark a ticket as completed or no-show
As a staff,
I want to click “Done” once the transaction is finished, or mark a ticket as “No-show” if the student doesn’t appear within a reasonable time,
so that the counter becomes free for the next person and the statistics are accurate.

Acceptance Criteria:

    A “Done” button appears next to the currently serving ticket. Clicking it changes the ticket status to “Completed” and releases the counter.

    A “No-show” button does the same but logs the ticket as a missed visit.

    Both actions trigger a real-time update to the display and remove the serving ticket.

## Actor 3: Administrator

US-07: Configure offices, services, and counters
As an administrator,
I want to add/edit/remove offices, their services, and assign staff to counters via a web panel,
so that the system adapts when services change or new offices are added.

Acceptance Criteria:

    Admin panel has a section for “Office Management” with CRUD operations.

    Each office can have multiple services (e.g., Registrar → Transcript, Enrollment Verification).

    Each service has a configurable estimated processing time (in minutes) that affects wait time calculation.

    Counters can be created and linked to one or more services/offices.

    Staff accounts are created separately and then assigned to counters.

### US-08: View real-time monitoring dashboard
As an administrator,
I want to see a live summary of all queues (waiting, serving, completed counts per office) and current staff activity,
so that I can spot bottlenecks and reallocate resources if needed.

Acceptance Criteria:

    Dashboard displays, for each office: number of waiting tickets, currently serving ticket, number of completed today.

    Data updates in real time (WebSocket push).

    Clicking on an office reveals a detailed list of waiting tickets and their wait times.

    The dashboard is read-only and accessible only after admin login.

### US-09: Generate queue reports
As an administrator,
I want to view and export daily/weekly reports showing total tickets served, average wait time, and staff productivity,
so that we can analyze service efficiency.

Acceptance Criteria:

    Report page allows selecting a date range.

    Shows: total tickets per service, average waiting time (from join to call), average serving time (call to completion), no-show count.

    Option to export as CSV/PDF (in a later version, CSV is enough for MVP).

    Data is aggregated from completed tickets.

## Actor 4: Public Display (System/Device)

### US-10: Display current serving information publicly
As a queue management system,
I want to have a dedicated webpage that can be opened on a large monitor in the waiting area,
so that all visitors can see which ticket is currently being served and upcoming calls.

Acceptance Criteria:

    A URL like /display/office_id shows a full-screen, auto-refreshing view.

    It shows:

        “Now Serving: R‑105 at Counter 3” (large font)

        Last 2–3 served tickets for reference

    The screen updates instantly when a staff member clicks “Call Next” or “Done.”

    Design is high-contrast and readable from a distance.

    Non-functional user stories (consider adding):

        As a student, I want the website to load on my mobile phone quickly and be easy to tap.

        As an admin, I want staff passwords to be hashed and not stored in plain text.

        As a stakeholder, I want the system to keep working if the internet blinks for a few seconds (graceful degradation or offline queuing – optional MVP).