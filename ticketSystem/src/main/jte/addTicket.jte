@template.layout(
title = "Add Ticket",
content = @`

    <h1>Create a New Ticket</h1>

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb my-4">
            <li class="breadcrumb-item">
                <a href="/tickets" class="text-decoration-none">Tickets</a>
            </li>
            <li class="breadcrumb-item text-muted">Create Ticket</li>
        </ol>
    </nav>

    <form action="/tickets/new" method="post">
        <div class="form-group">
            <label for="category">Category</label>
            <select class="form-control" id="category" name="category">
                <option value="Software">Software</option>
                <option value="Hardware">Hardware</option>
                <option value="Facilities">Facilities</option>
            </select>
        </div>

        <div class="form-group">
            <label for="subject">Subject</label>
            <input type="text" class="form-control" id="subject" name="subject" required>
        </div>

        <br>

        <button type="submit" class="btn btn-success">Create Ticket</button>
    </form>

`)
