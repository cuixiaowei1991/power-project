<html>
<body>
<h2>Hello World!</h2>
<script type="text/javascript" src="files/jquery.min.js"></script>
<script type="text/javascript" src="files/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="files/jquery.easing.min.js"></script>
<script>

    $(document).ready( function()
    {
//        setCookie("userCookie","{'userName':'王芳'}");
//        lottery();
    });

    function setCookie(name, value)
    {
        /*var oDate=new Date();

        oDate.setDate(oDate.getDate()+iDay);*/

        document.cookie=name+'='+encodeURIComponent(value);
    }
    function lottery() {

        var data = {'code':'code','version':'version','data':[{'activityId':'ff808181560c395e01560c3aeb3b0001'
            ,'appId':'wxa2b490a571ba7962','openId':'oSUlnuNxMGjIeMpTcxgaVINWk8lE'}]};
        $.ajax({
            type : 'POST',
            url : 'user/get',
            dataType : 'json',
            headers : {'Content-Type':'application/json'},
            data: JSON.stringify(data),
            cache : false,
            error : function() {
                alert('出错了！');
                return false;
            },
            success : function(json)
            {

            }
        });
    }

</script>
</body>
</html>
