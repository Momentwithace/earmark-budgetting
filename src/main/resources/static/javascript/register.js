const handleSubmit = async (event) => {
    event.preventDefault();
    const email = document.getElementById("register-email").value;
    const password = document.getElementById("register-password").value;
    const username = document.getElementById("register-username").value;
    if(email === null || email === "" ) {
        alert("Please enter a valid email");
        return;
    }
    if( username === null || username === ""){
        alert("Please enter a valid username");
        return;
    }
    if(password === null || password === ""){
        alert("Please enter a valid password");
        return;
    }
    const bodyObj = {
        username,
        password,
        email
    }

    fetch(`http://localhost:8080/api/v1/users/register`, {
        method: "POST", body: JSON.stringify(bodyObj), headers: {'Content-Type': "application/json"}
    }).then(res => res.json())
        .then(data => {
            if(data[0]==="success"){
                window.location.replace(data[1])
            }
            else alert(data[0])
        })
        .catch(err => console.log("err",err))

}

document.querySelector(".register-form").addEventListener("submit", handleSubmit)