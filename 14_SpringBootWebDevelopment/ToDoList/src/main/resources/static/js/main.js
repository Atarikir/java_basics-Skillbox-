$(function(){

    const appendTask = function(data){
        var taskCode = '<a href="#" class="task-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#task-list')
            .append('<div>' + taskCode + '</div>');
    };

    //Show adding task form
    $('#show-add-task-form').click(function(){
        $('#task-form').css('display', 'flex');
    });

    //Closing adding task form
    $('#task-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting task
    $(document).on('click', '.task-link', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + taskId,
            success: function(response)
            {
                var code = 'Описание дела:  ' + response.description;

                //ищем объекты span у родителя
                let desc_span = link.parent().find("span")

                //если их нет, то есть массив пустой
                if(desc_span.length === 0) {

                //создаем новый span и укажем ему id на основании id дела
                desc_span = $('<span>', {id: 'task-desk' + taskId});

                //добавим к родителю
                link.parent().append(desc_span);
                }

                //в любом случае зададим текст, тут уже точно объект есть
                desc_span.text(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Дело не найдено!');
                }
            }
        });
        return false;
    });

    //Adding task
    $('#save-task').click(function()
    {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response)
            {
                $('#task-form').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#task-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);

                let count = $('#todos-count')
                count.text(parseInt(count.text())+1)
            }
        });
        return false;
    });

});