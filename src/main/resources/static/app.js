const API_URL = "/tasks";

// Frontend state
let allTasks = [];
let currentFilter = "ALL";

// Load tasks when page loads
document.addEventListener("DOMContentLoaded", loadTasks);

function loadTasks() {
    fetch(API_URL)
        .then(response => response.json())
        .then(tasks => {
            allTasks = tasks;
            renderTasks();
        });
}

// Render tasks based on active filter
function renderTasks() {
    const list = document.getElementById("taskList");
    list.innerHTML = "";

    let filteredTasks = allTasks;

    if (currentFilter === "ACTIVE") {
        filteredTasks = allTasks.filter(task => !task.completed);
    } else if (currentFilter === "COMPLETED") {
        filteredTasks = allTasks.filter(task => task.completed);
    }

    filteredTasks.forEach(task => {
        const li = document.createElement("li");
        li.className = "task-item";

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = task.completed;
        checkbox.onchange = () => toggleTask(task.id, checkbox.checked);

        const span = document.createElement("span");
        span.textContent = task.title;
        if (task.completed) span.classList.add("completed");

        // Double-click to edit
        span.ondblclick = () => enableEdit(task, span);

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Delete";
        deleteBtn.onclick = () => deleteTask(task.id);

        li.appendChild(checkbox);
        li.appendChild(span);
        li.appendChild(deleteBtn);

        list.appendChild(li);
    });
}

// Enable inline edit
function enableEdit(task, span) {
    const input = document.createElement("input");
    input.type = "text";
    input.value = task.title;
    input.className = "task-edit-input";

    span.replaceWith(input);
    input.focus();

    const save = () => {
        const newTitle = input.value.trim();
        if (newTitle && newTitle !== task.title) {
            updateTitle(task.id, newTitle);
        } else {
            renderTasks();
        }
    };

    input.onblur = save;

    input.onkeydown = (e) => {
        if (e.key === "Enter") save();
        if (e.key === "Escape") renderTasks();
    };
}

// PATCH title
function updateTitle(id, title) {
    fetch(`${API_URL}/${id}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ title: title })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update title");
            }
            loadTasks();
        })
        .catch(error => alert(error.message));
}

// Add new task
function addTask() {
    const input = document.getElementById("taskInput");
    const title = input.value.trim();

    if (title === "") {
        alert("Task title cannot be empty");
        return;
    }

    fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            completed: false
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to create task");
            }
            return response.json();
        })
        .then(() => {
            input.value = "";
            loadTasks();
        })
        .catch(error => alert(error.message));
}

function toggleTask(id, completed) {
    fetch(`${API_URL}/${id}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ completed: completed })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update task");
            }
            loadTasks();
        })
        .catch(error => alert(error.message));
}

function deleteTask(id) {
    fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to delete task");
            }
            loadTasks();
        })
        .catch(error => alert(error.message));
}

// Filter control
function setFilter(filter) {
    currentFilter = filter;
    renderTasks();
}
