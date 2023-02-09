<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <div class="container">
                <form action="/join" method="post" onsubmit="return valid()">
                    <div class="form-group mb-2 d-flex">
                        <input type="text" name="username" class="form-control" placeholder="Enter username" required
                            id="username">
                        <button type="button" class="badge bg-secondary ms-2"> 중복 확인 </button>
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" name="password" class="form-control" placeholder="Enter password"
                            required id="password">
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" class="form-control" placeholder="Enter passwordCheck" required
                            id="passwordCheck">
                    </div>
                    <div id="passwordCheckAlert"></div>

                    <div class="form-group mb-2">
                        <input type="email" name="email" class="form-control" placeholder="Enter email" id="email"
                            required>
                    </div>

                    <button type="submit" class="btn btn-primary">회원가입</button>
                </form>

            </div>
        </div>

        <script>
            let checkPassword = false;

            function valid() {
                if (checkPassword == true) {
                    return true;
                }
                alert("회원가입 유효성 검사");
                return false;
            }

            function checkSamePassword() {
                let password = $("#password").val();
                let passwordCheck = $("#passwordCheck").val();
                if (password == undefined || password == "") {
                    return;
                }

                if (password == passwordCheck) {
                    checkPassword = true;
                    $("#passwordCheckAlert").empty();
                    let el = `<div class="text-success" id="passwordCheckAlert">
                                <strong>비밀번호 확인 완료!</strong>
                              </div>`
                    $("#passwordCheckAlert").append(el);
                } else {
                    checkPassword = false;
                    $("#passwordCheckAlert").empty();
                    let el = `<div class="text-danger">
                                <strong>비밀번호가 다릅니다!</strong>
                              </div>`
                    $("#passwordCheckAlert").append(el);
                }
            }
            $("#passwordCheck").keyup(() => {
                checkSamePassword();
            });
            $("#password").keyup(() => {
                checkSamePassword();
            });
        </script>
        <%@ include file="../layout/footer.jsp" %>