- IT Trouble Ticket Ticket System -



>>>> INTRO <<<<<
Main executable class is LOGIN.

A user is asked to login with specific credentials and then is cross referenced against DB to identify if admin or user.
Appropriate panel is then loaded



>>>>> Admin Panel <<<<<

Administrators have access to viewing all tickets in DB, Single Ticket view, editing tickets, closing tickets.
Also after each usage of the admin panel, upon exit, the DB is backed up through the backupData() process into a specific directory

All admin code is stored in AdminPanel class.


>>>> User Panel <<<<
Users have access to create new ticket, view single ticket, view all tickets, only submitted by them!!! (security)
Users can request for a ticket to be cancelled through their user interface to notify the admin. They can also check the status of their request
by clicking on view requests which checks the DB for status on their request ID.