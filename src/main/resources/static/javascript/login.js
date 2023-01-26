const handleSubmit = async (event) => {
    event.preventDefault();

    const password = document.getElementById("login-password").value;
    const username = document.getElementById("login-username").value;

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
        password
    }
    fetch(`http://localhost:8080/api/v1/users/login`,{
        method: "POST", body: JSON.stringify(bodyObj), headers:  {'Content-Type': "application/json"}
    }).then(res => res.json())
        .then(data => {
            if(data[0] === "Invalid Username or password") {
                alert(data[0])
                console.log("data", data[0])

            } else {
                document.cookie = `userId=${data[1]}`
                window.location.replace(data[0])
            }
        })


}
document.querySelector(".login-form").addEventListener("submit", handleSubmit)