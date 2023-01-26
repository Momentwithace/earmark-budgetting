if (document.cookie === "") {
    window.location.replace("login.html")
}
const headers = {'Content-Type': "application/json"}
const userId = Number(document.cookie.split("=")[2])
fetch(`http://localhost:8080/api/v1/users/${userId}`)
    .then(res => res.json())
    .then(data => {
        const {incomeHistory, logs, basicStat} = data
        const {
            lowestIncome,
            highestIncome,
            averageBudget,
            highestBudget,
            lowestBudget,
            averageIncome,
            income,
            budgetTotal
        } = basicStat

        $(".username").append(data.username.replace(/^./, data.username[0].toUpperCase()))
        $(".expense").append(budgetTotal);
        $(".income").append(income);
        $(".balance").append(Number(income - budgetTotal))

        $(".avg-income").html(averageIncome);
        $(".avg-budget").html(averageBudget);
        $(".highest-income").html(highestIncome)
        $(".lowest-income").html(lowestIncome);
        $(".high-budget").html(highestBudget);
        $(".low-budget").html(lowestBudget);

        incomeHistory.map(val => {
            $(".income-div").append(
                `<li class="list-group-item d-flex justify-content-between align-items-center item-el">
                        ${val.income} 
                         <button type="button" onclick="editIncome(${val.id})" class="btn btn-link" >edit</button>
                         <button type="button" onclick="deleteIncome(${val.id})" class="btn btn-link" >delete</button>
                    <span >${val.date}</span>
                 </li>`)
        })
        logs.map(x => {
            $(".logs").append(
                `<li class="list-group-item d-flex justify-content-between align-items-center item-el">
                        ${x.action}
                        <span >${x.date}</span>
                 </li>`)
        })
    })
const deleteIncome = (id) => {
    fetch(`http://localhost:8080/api/v1/users/income/${userId}/${id}`, {
        method: "DELETE", headers
    }).then(res => res.json()).then(data => window.location.reload())
}


const editIncome = (id) => {
    $('#editIncomeModal').modal('toggle')
    $(".edit-income-btn").click(() => {
        fetch(`http://localhost:8080/api/v1/users/income/update`, {
            method: "PUT",
            headers,
            body: JSON.stringify({userId, incomeId: id, newIncome: Number($("#edit-income").val())})
        }).then(res => res.json()).then(data => window.location.reload())
    })
}

fetch(`http://localhost:8080/api/v1/users/budget/${userId}`, {
    headers
}).then(res => res.json()).then(data => {
    data.map(budget => {
        $(".budget-div").append(`
            <li class="list-group-item d-flex justify-content-between align-items-center item-el">
                        ${budget.item} 
                        <span class="badge bg-primary rounded-pill price-el">$ ${budget.price}</span>
                        <button type="button" class="btn btn-link" onclick="editBudget(${budget.id})">edit</button>
                        <button onclick="deleteBudget(${budget.id})" type="button" class="btn btn-link">delete</button>               
             </li>`)
    })
})


$(".logout-btn").click(() => {
    let c = document.cookie.split(";")
    for (let i in c) {
        document.cookie = /^[^=]+/.exec(c[i])[0] + "=;expires=" + new Date(0).toUTCString()
    }
})


const deleteBudget = async (id) => {
    await fetch(`http://localhost:8080/api/v1/budgets/${userId}/${id}`, {
        method: "DELETE", headers
    })
        .then(res => res.json())
        .then(data => window.location.reload())
        .catch(err => console.error(err.message))
}

const editBudget = (id) => {
    $('#exampleModal2').modal('toggle')
    $(".edit-btn").click(() => {
        fetch(`http://localhost:8080/api/v1/budgets/updateItem`, {
            method: "PUT", headers,
            body: JSON.stringify({
                userId,
                budgetItemId: id,
                newItemName: $("#edit-item-name").val(),
                newItemPrice: Number($("#edit-item-price").val())
            })
        })
            .then(res => res.json())
            .then(data => window.location.reload())
            .catch(err => console.error(err.message))
    })
}

$(".submit-button").click((event) => {
    event.preventDefault();
    fetch(`http://localhost:8080/api/v1/budgets/addItem`, {
        method: "POST", body: JSON.stringify({
            item: $("#budget-input").val(),
            userId,
            price: Number($("#budget-amount").val())
        }),
        headers
    }).then(res => res.json())
        .then(data => window.location.reload())
})

$(".sbtn-income").click((event) => {
    event.preventDefault()
    fetch(`http://localhost:8080/api/v1/users/income`, {
        method: "POST", body: JSON.stringify({income: Number($("#income").val()), userId}),
        headers
    }).then(res => res.json())
        .then(data => window.location.reload())
        .catch(err => console.error(err.message))
})