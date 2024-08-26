package br.tv.ole.mock

import br.tv.ole.db.Message

val mockMessages = listOf(
    Message(id = "1", title = "Welcome", content = "Welcome to the app!", sent = 1627891200000, read = 1627894800000),
    Message(id = "2", title = "Update Available", content = "A new update is available for download.", sent = 1627987600000, read = 1627991200000),
    Message(id = "3", title = "Reminder", content = "Don't forget to check your tasks.", sent = 1628074000000, read = 1628077600000),
    Message(id = "4", title = "New Feature", content = "Check out our latest feature!", sent = 1628160400000, read = 1628164000000),
    Message(id = "5", title = "Survey", content = "We'd love to hear your feedback.", sent = 1628246800000, read = 1628250400000),
    Message(id = "6", title = "Discount Offer", content = "Get 20% off on your next purchase.", sent = 1628333200000, read = 1628336800000),
    Message(id = "7", title = "System Maintenance", content = "Scheduled maintenance at midnight.", sent = 1628419600000, read = 1628423200000),
    Message(id = "8", title = "Congratulations!", content = "You've won a prize!", sent = 1628506000000, read = 1628509600000),
    Message(id = "9", title = "Event Invitation", content = "Join us for an exclusive event.", sent = 1628592400000, read = 1628596000000),
    Message(id = "10", title = "Security Alert", content = "Suspicious login detected.", sent = 1628678800000, read = 1628682400000)
)
