from flask import Blueprint, abort, request, render_template,redirect, flash
import  requests
import json
from flask import flash

router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('template.html') 

#/////////////////////////////////////////////////////////
#Crud PROYECTO

@router.route('/proyecto/lista')
def listaProyecto(msg=''):
    r = requests.get('http://localhost:8099/myapp/proyecto/list')
    #print(type(r,json()))
    data = r.json()
    return render_template("proyecto/lista.html", lista = data["data"], message = msg) 

@router.route('/proyecto/registrar')
def registrarProyecto():
    # Obtener la lista de inversionistas
    r_inversionistas = requests.get('http://localhost:8099/myapp/inversionista/list')
    inversionistas_data = r_inversionistas.json()

    # Obtener la lista de propuestas
    r_propuestas = requests.get('http://localhost:8099/myapp/propuesta/list')
    propuestas_data = r_propuestas.json()

    # Pasar las listas a la plantilla para renderizar
    return render_template(
        'proyecto/registro.html', 
        lista_inversionistas=inversionistas_data['data'], 
        lista_propuestas=propuestas_data['data']
    )




    
@router.route('/proyecto/save', methods=['POST'])
def saveProyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Construir el objeto `dataF` con los datos del formulario
    dataF = {
        "nombre": form["nombre"],
        "fechaInicio": form["fechaInicio"],
        "fechaFin": form["fechaFin"],
        "estado": form["estado"],
        "inversionista": {"id": form["id"]},  # ID de inversionista seleccionado
        "propuesta": {"id": form["id"]}  # ID de propuesta seleccionada
    }
    

    # Hacer la solicitud POST al servicio REST en Java
    r = requests.post('http://localhost:8099/myapp/proyecto/save', data=json.dumps(dataF), headers=headers)
    dat = r.json()
    # Procesar la respuesta del servicio REST
    if r.status_code == 200:
        flash('Proyecto registrado con éxito', category='info')
        return redirect('/proyecto/lista')
    else:
        error_message = r.json().get('data', 'Error al registrar el proyecto')
        flash(error_message, category='error')
        return redirect('/proyecto/registrar')
    
@router.route('/proyecto/edit/<id>')
def editarProyecto(id):
    r=requests.get('http://localhost:8099/myapp/proyecto/list')
    data = r.json()
    r1=requests.get('http://localhost:8099/myapp/proyecto/get/'+id)
    data1 = r1.json()
    if(r1.status_code == 200):
        return render_template('proyecto/editarPr.html', lista = data["data"], inversionista = data1["data"], propuesta = data1["data"])
    else:
        flash(data1['data'], category='error')
        return redirect('/proyecto/lista') 
       
@router.route('/proyecto/update', methods=['POST'])
def updateproyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"id": form["id"], "nombre": form["nombre"], "fechaInicio": form["fechaInicio"], "fechaFin": form["fechaFin"], "estado": form["estado"], "inversionista": {"id": form["id"]}, "propuesta": {"id": form["id"]}}

    try:
        # Realiza la solicitud POST al servidor Java
        r = requests.post('http://localhost:8099/myapp/proyecto/update', data=json.dumps(dataF), headers=headers)
        dat = r.json()

        # Verifica el código de estado
        if r.status_code == 200:
            flash('Proyecto modificado con éxito', category='info')
        else:
            error_message = dat.get('data', 'Error al registrar el proyecto')
            flash(error_message, category='error')

        # Redirige a la lista después de procesar la solicitud
        return redirect('/proyecto/lista')

    except requests.exceptions.RequestException as e:
        # Maneja excepciones de la solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/proyecto/lista')
    





#////////////////////////////////////////////////////////////////////////////////////77
#Crud PROPUESTA
@router.route('/propuesta/registrar')
def registrarPropuesta():
    r=requests.get('http://localhost:8099/myapp/propuesta/list')
    data = r.json()
    print(r.json())
    return render_template('propuesta/registroP.html', lista = data["data"])

@router.route('/propuesta/listaP')
def listaPropuesta(msg=''):
    r = requests.get('http://localhost:8099/myapp/propuesta/list')
    #print(type(r,json()))
    data = r.json()
    return render_template("propuesta/listaP.html", lista = data["data"], message = msg)

@router.route('/propuesta/edit/<id>')
def editarPropuesta(id):
    r=requests.get('http://localhost:8099/myapp/propuesta/list')
    data = r.json()
    r1=requests.get('http://localhost:8099/myapp/propuesta/get/'+id)
    data1 = r1.json()
    if(r1.status_code == 200):
        return render_template('propuesta/editarP.html', lista = data["data"], propuesta = data1["data"])
    else:
        flash(data1['data'], category='error')
        return redirect('/propuesta/listaP')

@router.route('/propuesta/save', methods=['POST'])
def savePropuesta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Construye el JSON con los datos del formulario
    dataF = {
        "descripcion": form["descripcion"],
        "monto": form["monto"],
        "watsGenerados": form["watsGenerados"]
    }
    
    try:
        # Envía la solicitud POST al servicio Java
        r = requests.post('http://localhost:8099/myapp/propuesta/save', data=json.dumps(dataF), headers=headers)
        
        # Verifica el estado de la respuesta y si el contenido es JSON válido
        if r.status_code == 200:
            try:
                dat = r.json()  # Intenta decodificar la respuesta JSON
                flash('Propuesta registrada con éxito', category='info')
            except requests.exceptions.JSONDecodeError:
                flash('Propuesta registrada, pero la respuesta del servidor no es JSON válida.', category='info')
        else:
            try:
                # Si el estado no es 200, intenta obtener el mensaje de error del JSON
                error_message = r.json().get('data', 'Error al registrar la propuesta')
            except requests.exceptions.JSONDecodeError:
                # Si el JSON no es válido, usa un mensaje genérico
                error_message = 'Error desconocido al registrar la propuesta'
            flash(error_message, category='error')
        
        return redirect('/propuesta/listaP')
    
    except requests.exceptions.RequestException as e:
        # Manejo de errores de conexión o solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/propuesta/listaP')
    
@router.route('/propuesta/update', methods=['POST'])
def updatepropuesta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"id": form["id"], "descripcion": form["descripcion"], "monto": form["monto"], "watsGenerados": form["watsGenerados"]}

    try:
        # Realiza la solicitud POST al servidor Java
        r = requests.post('http://localhost:8099/myapp/propuesta/update', data=json.dumps(dataF), headers=headers)
        dat = r.json()

        # Verifica el código de estado
        if r.status_code == 200:
            flash('Propuesta modificado con éxito', category='info')
        else:
            error_message = dat.get('data', 'Error al registrar el propuesta')
            flash(error_message, category='error')

        # Redirige a la lista después de procesar la solicitud
        return redirect('/propuesta/listaP')

    except requests.exceptions.RequestException as e:
        # Maneja excepciones de la solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/propuesta/listaP')


   



#///////////////////////////////////////////////////////////////////////////////////
#Crud INVERSIONISTA
@router.route('/inversionista/listaI')
def listaInversionista(msg=''):
    r = requests.get('http://localhost:8099/myapp/inversionista/list')
    #print(type(r,json()))
    data = r.json()
    return render_template("inversionista/listaI.html", lista = data["data"], message = msg)

@router.route('/inversionista/registrar')
def registrarInversionista():
    r=requests.get('http://localhost:8099/myapp/inversionista/list')
    data = r.json()
    return render_template('inversionista/registroI.html', lista = data["data"])

@router.route('/inversionista/editar/<id>')
def editarInversionista(id):

    r1=requests.get('http://localhost:8099/myapp/inversionista/get/'+id)
    data1 = r1.json()
    if(r1.status_code == 200):
        return render_template('inversionista/editar.html', inversionista = data1["data"])
    else:
        flash(data1['data'], category='error')
        return redirect('/inversionista/listaI')

@router.route('/inversionista/save', methods=['POST'])
def saveInversionista():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"nombre": form["nombre"], "apellido": form["apellido"],"dni": form["dni"], "montoI": form["montoI"]}

    try:
        # Realiza la solicitud POST al servidor Java
        r = requests.post('http://localhost:8099/myapp/inversionista/save', data=json.dumps(dataF), headers=headers)
        dat = r.json()

        # Verifica el código de estado
        if r.status_code == 200:
            flash('Inversionista registrado con éxito', category='info')
        else:
            error_message = dat.get('data', 'Error al registrar el inversionista')
            flash(error_message, category='error')

        # Redirige a la lista después de procesar la solicitud
        return redirect('/inversionista/listaI')

    except requests.exceptions.RequestException as e:
        # Maneja excepciones de la solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/inversionista/listaI')
    
@router.route('/inversionista/update', methods=['POST'])
def updateInversionista():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {"nombre": form["nombre"], "apellido": form["apellido"],"dni":form["dni"], "montoI": form["montoI"]}

    try:
        # Realiza la solicitud POST al servidor Java
        r = requests.post('http://localhost:8099/myapp/inversionista/save', data=json.dumps(dataF), headers=headers)
        dat = r.json()

        # Verifica el código de estado
        if r.status_code == 200:
            flash('Inversionista registrado con éxito', category='info')
        else:
            error_message = dat.get('data', 'Error al registrar el inversionista')
            flash(error_message, category='error')

        # Redirige a la lista después de procesar la solicitud
        return redirect('/inversionista/listaI')

    except requests.exceptions.RequestException as e:
        # Maneja excepciones de la solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/inversionista/listaI')
    
@router.route('/inversionista/search/<criterio>/<texto>')
def view_buscar_person(criterio, texto):
    url = "http://localhost:8099/myapp/inversionista/list/search/"
    if criterio == "apellido":
        r = request.get(url + texto)  # Asegúrate de que esta URL sea correcta
    elif criterio == "dni":
        r = request.get(url + "ident/" + texto)

    data1 = r.json()
    if r.status_code == 200:
        if isinstance(data1["data"], dict):  # Verifica si el tipo es un diccionario
            list = []
            list.append(data1["data"])
            return render_template('fragmento/persona/lista.html', list=list)
        else:
            return render_template('fragmento/persona/lista.html', list=data1["data"])
    else:
        return render_template('fragmento/persona/lista.html', lista=[], message='No existe el elemento')
    
@router.route('/proyecto/sort', methods=['POST'])
def sort_projects():
    try:
        # Obtener los parámetros enviados en el cuerpo de la solicitud
        params = request.get_json()

        # Validar que los parámetros necesarios estén presentes
        if "campoOrden" in params and "direccion" in params and "algoritmo" in params:
            campo_orden = params["campoOrden"]  # El campo por el cual ordenar
            direccion = int(params["direccion"])  # 1 para ascendente, 2 para descendente
            algoritmo = int(params["algoritmo"])  # Algoritmo de ordenación: 1 = QuickSort, 2 = MergeSort, 3 = ShellSort

            # Llamar al servicio para ordenar los proyectos
            proyecto_service = ProyectoServices()
            sorted_projects = proyecto_service.ordenar_proyectos(campo_orden, direccion, algoritmo)

            # Preparar la respuesta
            res = {
                "msg": "OK",
                "data": sorted_projects
            }
            return jsonify(res), 200
        else:
            # Faltan parámetros requeridos
            return jsonify({
                "msg": "Error",
                "data": "Faltan parámetros requeridos: campoOrden, direccion, algoritmo"
            }), 400

    except Exception as e:
        # Manejo de excepciones
        print(f"Error al ordenar proyectos: {e}")
        return jsonify({
            "msg": "Error",
            "data": str(e)
        }), 500
