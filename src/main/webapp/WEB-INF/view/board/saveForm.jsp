<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file ="../layout/header.jsp"%>
    <div class="container my-3">
       <form class="mb-1">
            <div class="form-group mb-2">
                <input type="text" class="form-control" placeholder="Enter title" name="title" id="title">
            </div>
            <div class="form-group mb-2">
                <textarea class="form-control summernote" rows="5" id="content" name="content"></textarea>
            </div>
            <button type="button" class="btn btn-primary" onClick="save()">글쓰기완료</button>
        </form>
    </div>

    <script>
        function save() {
            let data={
                "title": $("#title").val(),
                "content": $("#content").val()
            };
            $.ajax({
                type: "post",
                url: "/board",
                data: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                    },
                dataType: "json" 
                })
                .done(res => { //20X 일때
                    alert(res.msg);
                    location.href ="/board";
                })
                .fail(err => { 
                    alert(err.responseJSON.msg);
                });
        }
    </script>
    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
    </script>
<%@ include file ="../layout/footer.jsp"%>