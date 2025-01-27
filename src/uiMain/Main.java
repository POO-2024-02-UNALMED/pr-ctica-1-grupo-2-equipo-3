/* Autores: Samuel Botero Rivera, Santiago Sanchez Ruiz, Samuel Gutierrez Betancur, Samuel Garcia Rojas  */
package uiMain;

import baseDatos.Serializador;
import gestorAplicacion.administracion.CategoriaHabitacion;
import gestorAplicacion.administracion.HistoriaClinica;
import gestorAplicacion.administracion.Hospital;
import gestorAplicacion.administracion.Medicamento;
import gestorAplicacion.administracion.Vacuna;
import gestorAplicacion.personas.Doctor;
import gestorAplicacion.personas.Enfermedad;
import gestorAplicacion.personas.Paciente;
import gestorAplicacion.servicios.Cita;
import gestorAplicacion.servicios.CitaVacuna;
import gestorAplicacion.servicios.Formula;
import gestorAplicacion.servicios.Habitacion;
import gestorAplicacion.servicios.Servicio;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Hospital hospital = new Hospital(); // Inicio de instancia de hospital para serialización
        mostrarUtilidadPrograma();
        menuInicial(hospital);
    }

    public static void menuInicial(Hospital hospital) {

        byte opcion; // Se inicia la variable para elegir la opción que se selecciona

        do {
            // Inicio del programa, solo se selecciona a que menú se desea acceder
            System.out.println("\nMENU INICIAL");
            System.out.println("1. Servicios para pacientes");
            System.out.println("2. Gestionar registros");
            System.out.println("3. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();

            switch (opcion) {
                case 1 -> MenuFuncionalidades.menuFuncionalidades(hospital);
                case 2 -> MenuGestion.menuGestion(hospital);
                case 3 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }
    public static void mostrarUtilidadPrograma() {
        System.out.println("Utilidadez del programa:");
        System.out.println("- Gestión de pacientes, doctores y medicamentos.");
        System.out.println("- Asignación de habitaciones en el hospital.");
        System.out.println("- Registro y manejo de citas médicas y de vacunación.");
        System.out.println("- Generación de facturas y control de pagos.");
        System.out.println("- Manejo de inventario de medicamentos y vacunas.");
    }
public static void menuFuncionalidades(Hospital hospital) {
        byte opcion;

        do {
            System.out.println("\nMENU FUNCIONALIDADES");
            System.out.println("1. Agendar una cita medica");
            System.out.println("2. Generar fórmula médica");
            System.out.println("3. Asignar habitación a un paciente");
            System.out.println("4. Aplicarse una vacuna");
            System.out.println("5. Facturacion");
            System.out.println("6. --Regresar al menu inicial--");
            System.out.println("7. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();

            switch (opcion) {
                case 1 -> agendarCitas(hospital);
                case 2 -> formulaMedica(hospital);
                case 3 -> asignarHabitacion(hospital);
                case 4 -> vacunacion(hospital);
                case 5 -> facturacion(hospital);
                case 6 -> {
                    return;
                }
                case 7 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }

public static void menuGestion(Hospital hospital) {
        byte opcion;
        // Menú para gestionar las clases del programa
        do {
            System.out.println("\nMENU Gestion");
            System.out.println("1. Gestionar Pacientes");
            System.out.println("2. Gestionar apartado de vacunas");
            System.out.println("3. Gestionar Doctores");
            System.out.println("4. Gestionar Hospital");
            System.out.println("5. --Regresar al menu inicial--");
            System.out.println("6. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();
            switch (opcion) {
                case 1 -> menuGestionPaciente(hospital);
                case 2 -> menuGestionVacunas(hospital);
                case 3 -> menuGestionDoctor(hospital);
                case 4 -> menuGetionHospital(hospital);
                case 5 -> {
                    return;
                }
                case 6 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }




	///////////////////////////////// FUNCIONALIDADES ////////////////////////////////////////
	
	public static void agendarCitas(Hospital hospital) {

		//pide la cedula y busca el paciente
        System.out.print("Ingrese su numero de cedula: ");

		int numeroCedula;
		numeroCedula = sc.nextInt();

		Paciente pacienteAsignado = hospital.buscarPaciente(numeroCedula);

		//Verificación que el paciente se encuentre en la base de datos del hospital

        if (pacienteAsignado == null){
            while(true){
                //Se da la posibilidad de registrar ese paciente
                System.out.println("El paciente no está registrado.\n¿Desea registrarlo?");
                System.out.println("1. Si\n2.No \nSeleccione una opción");
                byte opcion= sc.nextByte();
                switch (opcion){
                    case 1:
                        registrarPaciente(hospital);
                        return;

                    case 2:
                        System.out.println("Adiós");
                        return;

                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }

		//Mensaje de bienvenida
		System.out.println(pacienteAsignado.bienvenida());

		//se crea un arraylit de doctores que se usara mas adelante
		ArrayList<Doctor> doctoresDisponibles = new ArrayList<Doctor>();

		//ciclo while para cuando no hay doctores del tipo de eps del paciente en la especialidad seleccionada
		while (doctoresDisponibles.size()==0) {

		//menu para elegir el tipo de cita
		byte tipoCita;

		System.out.println("\nSeleccione el tipo de cita que requiere");
		System.out.println("1. General");
		System.out.println("2. Odontologia");
		System.out.println("3. Oftalmologia");
		System.out.println("4. --Regresar al menu--");
		System.out.print("Ingrese una opcion: ");

		tipoCita = sc.nextByte();

		//ciclo while para casos de error
		while ((tipoCita<1) ||(tipoCita>4) ){
            System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
            tipoCita = sc.nextByte();
		}

		//muestra los doctores disponibles segun el tipo de cita y eps del paciente
		switch (tipoCita) {
		case 1:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("General", hospital);

			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de doctores Generales para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
			for(int i=1; i<=doctoresDisponibles.size(); i++) {
	    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
			}
		}
			else {
				System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
			}
			break;

		case 2:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("Odontologia", hospital);
			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de odontologos para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
				for(int i=1; i<=doctoresDisponibles.size(); i++) {
		    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
				}
			}
				else {
					System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
				}
			break;

		case 3:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("Oftalmologia", hospital);
			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de oftalmologos para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
				for(int i=1; i<=doctoresDisponibles.size(); i++) {
		    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
				}
			}
				else {
					System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
				}
			break;

			case 4:
				return;
		}
	}
		//se crea un arraylit de citas del doctor seleccionado
		ArrayList<Cita> agendaDisponible = new ArrayList<Cita>();

		//ciclo while para cuando el doctor seleccionado no tiene citas disponibles
		while(agendaDisponible.size()==0){
			//elige un doctor de la lista anterior
			System.out.print("\nSeleccione el doctor con el que quiere la cita: ");

			byte numeroDoctor;
			numeroDoctor = sc.nextByte();

			//ciclo while para casos de error
			while ((numeroDoctor<1) || numeroDoctor>doctoresDisponibles.size()+1){
				System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
				numeroDoctor = sc.nextByte();
			}

			if(numeroDoctor==doctoresDisponibles.size()+1){return;}

			Doctor doctorAsignado = doctoresDisponibles.get(numeroDoctor-1);
			agendaDisponible = doctorAsignado.mostrarAgendaDisponible();

			if(agendaDisponible.size()>0) {
				//muestra la agenda disponible del doctor seleccionado
				System.out.println("\nCitas disponibles: ");
				for (int i = 1; i <= agendaDisponible.size(); i++) {
					System.out.println(i + ". " + agendaDisponible.get(i - 1).getFecha());
				}

				//selecciona la cita de la agenda disponible del doctor
				System.out.print("\nSeleccione la cita de su preferencia: ");

				byte numeroCita;
				numeroCita = sc.nextByte();

				//ciclo while para casos de error
				while ((numeroCita<1) || numeroCita>agendaDisponible.size()){
					System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
					numeroCita = sc.nextByte();
				}

				//actualiza la agenda del doctor con el paciente
				Cita citaAsignada = doctorAsignado.actualizarAgenda(pacienteAsignado, numeroCita, agendaDisponible);

				System.out.println("\nSu cita ha sido asignada con exito");

				//actualiza el historial de citas del paciente
				pacienteAsignado.actualizarHistorialCitas(citaAsignada);


				//resumen de la cita
				System.out.println("\nResumen de su cita: ");
				System.out.println("Fecha: " + citaAsignada.getFecha());
				System.out.println("Paciente: " + citaAsignada.getPaciente().getNombre());
				System.out.println("Doctor: " + citaAsignada.getDoctor().getNombre());


				//limpia los arrays de doctores y agenda disponibles al haberse asignado un paciente a una cita
				agendaDisponible.clear();
				doctoresDisponibles.clear();

				//muestra el historial de las citas que ha tenido el paciente
				System.out.println("\nhistorial citas de historia clinica del paciente: ");
				for(int i=1; i<=pacienteAsignado.getHistoriaClinica().getHistorialCitas().size(); i++) {
					System.out.println("");
					System.out.println("Fecha: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getFecha());
					System.out.println("Paciente: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getPaciente().getNombre());
					System.out.println("Doctor: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getDoctor().getNombre());
				}

				//Despedida
				System.out.println("\n"+pacienteAsignado.despedida(citaAsignada));
				break;
			}
			else{
				System.out.println("\nEste doctor no tiene citas disponibles, por favor seleccione otro");
			}
		}
	}
	
	public static void formulaMedica(Hospital hospital) {
        //Se pide la cedula del paciente y se arroja el paciente
        System.out.println("Ingrese la cédula del paciente: ");
        int cedula = sc.nextInt();
        Paciente paciente = hospital.buscarPaciente(cedula);
        if (paciente == null) { /*Si el paciente es null, quiere decir que no lo encontró, por lo que
        preguntamos si desea registrar este paciente */
            while (true) {
                System.out.println("El paciente no esta registrado.\n¿Desea registrarlo?");
                System.out.println("1. Si\n2. No \nSeleccione una opción");
                byte opcion = sc.nextByte();
                switch (opcion) {
                    case 1:
                        registrarPaciente(hospital);
                        return;

                    case 2:
                        System.out.println("Adios");
                        return;
                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }
        // se crea ya la formula con el paciente asociado
        Formula formulaPaciente = new Formula(paciente);
        // se inicia su lista de medicamentos solicitada vacia
        ArrayList<Medicamento> listMedicamento = new ArrayList<Medicamento>();
        // se pregunta al paciente que enfermedad desea tratar y se busca la lista de enfermedades en su historia
        System.out.println("¿Que enfermedad deseas tratar?");
        Enfermedad enfermedadTratar = null;
        if (paciente.getHistoriaClinica().getEnfermedades().size() == 0){ //Caso en el que el paciente no tenga enfermedades registradas
            System.out.println("No hay enfermedades registradas, por favor diríjase a la sección de registrar enfermedades.");
            return;
        }
        while (true) { //Bucle para el caso en el que elija opciones fuera de rango
            for (int i = 0; i < paciente.getHistoriaClinica().getEnfermedades().size(); i++) {
                System.out.println(i + 1 + "." + paciente.getHistoriaClinica().getEnfermedades().get(i));
            }
            byte opcEnf = sc.nextByte();
            if (opcEnf <= paciente.getHistoriaClinica().getEnfermedades().size() && opcEnf > 0) {
                enfermedadTratar = paciente.getHistoriaClinica().getEnfermedades().get(opcEnf - 1); //Aquí seleccionamos que enfermedad eligió el paciente a tratar
                break;

            } else {
                System.out.println("Opción Inválida");
            }
        }
        ArrayList<Doctor> doctoresCita = paciente.getHistoriaClinica().buscarCitaDoc(enfermedadTratar.getEspecialidad(), hospital); /* metodo de la historia del paciente
         para buscar que doctor trata la enfermedad y que haya tenido una cita con el paciente */
        if (doctoresCita.size() == 0) { //Caso donde no tenga citas con ningún doctor que trate su enfermedad
            System.out.println("Ahora no contamos con doctores para tratar esta enfermedad. Lo sentimos mucho");
            return;
        }
        Doctor doctorEscogido = null;
        while (true) { //Bucle para el caso en el que elija opciones fuera de rango
            System.out.println("Los doctores que lo han atendido y están disponibles para formular " + enfermedadTratar.getNombre() + " " + enfermedadTratar.getTipologia() + " son: ");
            //Imprime la cita de los doctores que tratan su enfermedad y tuvieron una cita con él
            for (int i = 0; i < doctoresCita.size(); i++) {
                System.out.println(i + 1 + "." + doctoresCita.get(i).getNombre());
            }
            byte opcDoc = sc.nextByte();
            if (opcDoc > 0 & opcDoc <= doctoresCita.size()) {
                doctorEscogido = doctoresCita.get(opcDoc - 1); //seleccionamos el doctor
                break;
            } else {
                System.out.println("Opción inválida");
            }
        }
        boolean agregarOtro = false; //condicion para el while para ir agregando medicamentos

        formulaPaciente.setDoctor(doctorEscogido);
        //bucle para agregar medicamentos, cada vez q pregunte si desea otro y responda si se repite
        do {
            System.out.println(paciente.mensajeDoctor(doctorEscogido));
            //Aquí buscamos los medicamentos disponibles y los medicamentos que traten la enfermedad del paciente
            ArrayList<Medicamento> disponibleEnf = paciente.medEnfermedad(enfermedadTratar, hospital);
            //Condicion para que cuando ya no haya medicamentos disponibles no siga el bucle y lo termine
            if (disponibleEnf.size()==0){
                System.out.println("No hay más medicamentos disponibles");
                break;
            }
            while (true) { //Bucle para el caso en el que elija opciones fuera de rango
                //esto solo se usa para imprimir la lista de la forma 1. Nombre, etc..
                for (int i = 0; i < disponibleEnf.size(); i++) {
                    System.out.println(i + 1 + "." + disponibleEnf.get(i) + ", Cantidad: " + disponibleEnf.get(i).getCantidad() + ", Precio: " + disponibleEnf.get(i).getPrecio());
                }
                //guarda la opcion elegida
                byte opcMed = sc.nextByte();
                if (opcMed <= 0 || opcMed > disponibleEnf.size()) {
                    System.out.println("Opción inválida");
                }
                /* si la opcion es valida se agrega a la lista de medicamentos que esta fuera del bucle y se ejecuta eliminarCantidad()
                para actualizar la cantidad de medicamentos en inventario */
                else {
                    Medicamento medicamentoEscogido = disponibleEnf.get(opcMed - 1);
                    medicamentoEscogido.eliminarCantidad();
                    listMedicamento.add(medicamentoEscogido);
                    break;
                }
            }
            /* Estas dos lineas son para ir actualizando la lista de medicamentos de la formula e ir actualizando el precio,
             se usa el metodo calcularPrecio de paciente, el cual se mete como parametro la lista de medicamentos que vamos llenando */
            formulaPaciente.setListaMedicamentos(listMedicamento);
            System.out.println("Esta es tu lista actual de medicamentos:" + listMedicamento);
            // Esto solo imprime los medicamentos que van
            for (int i = 0; i < listMedicamento.size(); i++) {
                System.out.println(i + 1 + "." + listMedicamento.get(i));
            }
            while (true) { //Bucle para el caso en el que se ingresen opciones incorrectas
                System.out.println("¿Desea agregar otro medicamento? (s/n)");
                char agregar = sc.next().charAt(0);
                if (agregar == 's' || agregar == 'n') {
                    agregarOtro = agregar == 's';
                    break;
                } else {
                    System.out.println("Opción inválida");
                }
            }
        } while (agregarOtro);
        if (listMedicamento.size() == 0) { /* En caso de que listaMedicamentos este vacía significa que no formulo medicamentos
         entonces que esa formula vacía creada al inicio de la funcionalidad no se guarde en la historia del paciente */
            return;
        }
        paciente.getHistoriaClinica().agregarFormula(formulaPaciente);
        //esto solo imprime el toString de la formula
        System.out.println(formulaPaciente);
        listMedicamento.clear();
    }

    public static void asignarHabitacion(Hospital hospital) {

        //Se pide la cedula del paciente y devuelve el paciente encontrado
        System.out.println("Ingrese el número de identificación del paciente:");
        int cedula = sc.nextInt();
        sc.nextLine();
        Paciente paciente = hospital.buscarPaciente(cedula);

        if (paciente != null) { //se comprueba que el paciente sea diferete de null para poder continuar
            if (paciente.getHabitacionAsignada() == null) { //se comprueba que el atributo sea null para poder seleccionar una habitacion
                //se declaran unos atributos necesarios para la funcionalidad
                Habitacion habitacion = null;
                Habitacion habitacionSeleccionada = null;
                Habitacion otraHabitacion = null;
                ArrayList<Habitacion> habitacionesDisponibles = new ArrayList<>();
                ArrayList<Habitacion> otraHabitacionDisponibles = new ArrayList<>();
                //del atributo tipoEps se comprueba si es tipo sucidiado para mostrar la categoria de habitaciones disponibles que la eps le permite
                if (paciente.getTipoEps().equals("Subsidiado")) {
                    int eleccion;
                    do { // se emplea un swicth para mostrar las categorias disponibles
                        System.out.println("Elija el tipo de habitacion que desee, recuerde que segun el tipo va el costo del servicio");
                        System.out.println("1. CAMILLA");
                        System.out.println("2. OBSERVACION");
                        System.out.println("3. UCI");
                        System.out.print("Ingrese una opción: ");
                        eleccion = sc.nextInt();
                        sc.nextLine();


                        switch (eleccion) {
                            case 1:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.CAMILLA);
                                //se envia la categoria que selecciono al atributo correspondiente que este caso es categoriaHabitacion
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                //con el atributo anterior se llama el metodo BuscarHabitacionDisponible y se pasa como paramatro el atributo anterior
                                if (habitacionesDisponibles != null) {
                                    //se comprueba si lo que nos retorno el metodo anterior es diferente a null
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    // se recorre la lista que nos retorno y muestran en la consola una lista numerada de las habitaciones disponibles, donde cada habitación se identifica por su índice y número
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion;
                                    //se pide que seleccione la habitación que mas le guste y que cumple el rango establecido
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion = sc.nextInt();
                                    } while (opcion < 0 || opcion >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion);

                                    habitacion = habitacionSeleccionada;

                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }

                            case 2:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.OBSERVACION);
                                //se envia la categoria que selecciono al atributo correspondiente que este caso es categoriaHabitacion
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                //con el atributo anterior se llama el metodo BuscarHabitacionDisponible y se pasa como paramatro el atributo anterior
                                if (habitacionesDisponibles != null) {
                                    //se comprueba si lo que nos retorno el metodo anterior es diferente a null
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    // se recorre la lista que nos retorno y muestran en la consola una lista numerada de las habitaciones disponibles, donde cada habitación se identifica por su índice y número
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion1;
                                    //se pide que seleccione la habitación que mas le guste y que cumple el rango establecido
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion1 = sc.nextInt();
                                    } while (opcion1 < 0 || opcion1 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion1);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion1);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 3:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.UCI);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion2;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion2 = sc.nextInt();
                                    } while (opcion2 < 0 || opcion2 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion2);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion2);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            default:
                                System.out.println("Opción inválida. Por favor, intente de nuevo.");
                                break;
                        }

                    } while (eleccion != 1 && eleccion != 2 && eleccion != 3);

                }//del atributo tipoEps se comprueba si es tipo contributivo para mostrar la categoria de habitaciones disponibles que la eps le permite y se hace lo mismo con las categorias que en sucidiado
                //cabe aclarar que contributivo tiene mas categorias disponibles
                else if (paciente.getTipoEps().equals("Contributivo")) {
                    int eleccion;
                    do {
                        System.out.println("Elija el tipo de habitacion que desee, recuerde que segun el tipo va el costo del servicio");
                        System.out.println("1. INDIVIDUAL");
                        System.out.println("2. DOBLE");
                        System.out.println("3. OBSERVACION");
                        System.out.println("4. UCI");
                        System.out.println("5. UCC");
                        System.out.print("Ingrese una opción: ");
                        eleccion = sc.nextInt();
                        sc.nextLine();


                        switch (eleccion) {
                            case 1:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.INDIVIDUAL);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion = sc.nextInt();
                                    } while (opcion < 0 || opcion >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 2:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.DOBLE);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion1;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion1 = sc.nextInt();
                                    } while (opcion1 < 0 || opcion1 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion1);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion1);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 3:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.OBSERVACION);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion2;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion2 = sc.nextInt();
                                    } while (opcion2 < 0 || opcion2 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion2);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion2);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 4:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.UCI);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion3;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion3 = sc.nextInt();
                                    } while (opcion3 < 0 || opcion3 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion3);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion3);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 5:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.UCC);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion4;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion4 = sc.nextInt();
                                    } while (opcion4 < 0 || opcion4 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion4);


                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion4);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            default:
                                System.out.println("Opción inválida. Por favor, intente de nuevo.");
                                break;
                        }
                    } while (eleccion != 1 && eleccion != 2 && eleccion != 3 && eleccion != 4 && eleccion != 5);

                } else { //si no cumple ninguno de los parametros anteriores se hace lo mismo pero con menos categorias disponibles
                    int eleccion;
                    do {

                        System.out.println("Elija el tipo de habitacion que desee, recuerde que segun el tipo va el costo del servicio");
                        System.out.println("1. CAMILLA");
                        System.out.println("2. UCI");
                        System.out.print("Ingrese una opción: ");
                        eleccion = sc.nextInt();
                        sc.nextLine();


                        switch (eleccion) {
                            case 1:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.CAMILLA);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion = sc.nextInt();
                                    } while (opcion < 0 || opcion >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion);

                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }
                            case 2:
                                paciente.setCategoriaHabitacion(CategoriaHabitacion.UCI);
                                habitacionesDisponibles = Habitacion.BuscarHabitacionDisponible(paciente.getCategoriaHabitacion());
                                if (habitacionesDisponibles != null) {
                                    System.out.println("Habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion() + ":");
                                    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
                                        Habitacion habi = habitacionesDisponibles.get(i);
                                        System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                                    }
                                    sc = new Scanner(System.in);
                                    int opcion1;
                                    do {
                                        System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                                (habitacionesDisponibles.size() - 1) + "):");
                                        opcion1 = sc.nextInt();
                                    } while (opcion1 < 0 || opcion1 >= habitacionesDisponibles.size());


                                    habitacionSeleccionada = habitacionesDisponibles.get(opcion1);

                                    // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                                    habitacionesDisponibles.remove(opcion1);

                                    habitacion = habitacionSeleccionada;
                                    break;
                                } else {
                                    habitacion = null;
                                    break;
                                }

                            default:
                                System.out.println("Opción inválida. Por favor, intente de nuevo.");
                                break;
                        }
                    } while (eleccion != 1 && eleccion != 2);
                }


                if (habitacion != null) { //se comprueba que habitacion sea diferente a null
                    habitacion.setOcupada(true);
                    habitacion.setPaciente(paciente);
                    int index = -1;
                    for (int i = 0; i < hospital.habitaciones.size(); i++) {
                        if (hospital.habitaciones.get(i).equals(habitacion)) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        hospital.habitaciones.set(index, habitacion);
                    }
                    paciente.setHabitacionAsignada(habitacion);
                    //se asigna la habitacion al paciente
                    sc = new Scanner(System.in);
                    System.out.println("Ingrese el número estimado de dias para la estadia en la habitacion:");
                    //se pide los dias estimados de la estadia en la habitacion
                    int dias = sc.nextInt();
                    sc.nextLine();
                    paciente.getHabitacionAsignada().setDias(dias);
                    System.out.println("\nDatos de la habitacion del Paciente: ");
                    System.out.println("Número de ID de la habitación: " + habitacion.getNumero());
                    System.out.println("Categoria de la habitación: " + habitacion.getCategoria());
                    System.out.println("Cedula del Paciente: " + paciente.getCedula());
                    System.out.println("Nombre del Paciente: "+ paciente.getNombre());

                } else { // si la habitacion es null se imprime una ocion al usuario de escoger si desea cambiar de categoria a una inferior
                    System.out.println("No hay habitaciones disponibles de la categoría " + paciente.getCategoriaHabitacion());
                    System.out.println("¿Desea asignar una habitación de otra categoría inferior? (s/n)");
                    sc = new Scanner(System.in);
                    String respuesta = sc.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        //se ejecuta un metodo para cambiar de caterioria y se buscan las habitaciones disponibles de esa categoria
                        CategoriaHabitacion otraCategoria = Habitacion.BuscarOtraCategoria(paciente.getCategoriaHabitacion());
                        otraHabitacionDisponibles = Habitacion.BuscarHabitacionDisponible(otraCategoria);
                        if (otraHabitacionDisponibles != null) {//se comprueba que la nueva categoria tenga habitaciones y se hace lo mismo al momento de elejir la habitacion q
                            System.out.println("Habitaciones disponibles de la categoría inferor:");
                            for (int i = 0; i < otraHabitacionDisponibles.size(); i++) {
                                Habitacion habi = otraHabitacionDisponibles.get(i);
                                System.out.println(i + ". " + "Número ID: "+ habi.getNumero());
                            }
                            sc = new Scanner(System.in);
                            int opcion;
                            do {
                                System.out.println("Seleccione la habitación deseada (ingrese un número del 0 al " +
                                        (otraHabitacionDisponibles.size() - 1) + "):");
                                opcion = sc.nextInt();
                            } while (opcion < 0 || opcion >= otraHabitacionDisponibles.size());
                            habitacionSeleccionada = otraHabitacionDisponibles.get(opcion);

                            // Eliminar la habitación seleccionada de la lista de habitaciones disponibles
                            otraHabitacionDisponibles.remove(opcion);

                            otraHabitacion = habitacionSeleccionada;
                        } else {
                            otraHabitacion = null;
                        }
                        if (otraHabitacion != null) {//se vuelve a comprobar que la otraHabitacion sea diferente a null
                            otraHabitacion.setOcupada(true);
                            int index = -1;
                            for (int i = 0; i < hospital.habitaciones.size(); i++) {
                                if (hospital.habitaciones.get(i).equals(otraHabitacion)) {
                                    index = i;
                                    break;
                                }
                            }
                            if (index != -1) {
                                hospital.habitaciones.set(index, otraHabitacion);
                            }
                            otraHabitacion.setPaciente(paciente);
                            paciente.setHabitacionAsignada(otraHabitacion);
                            sc = new Scanner(System.in);
                            System.out.println("\"Ingrese el número estimado de dias para la estadia en la habitacion:");
                            int dias = sc.nextInt();
                            sc.nextLine();
                            paciente.getHabitacionAsignada().setDias(dias);
                            System.out.println("\nDatos de la habitacion del Paciente: ");
                            System.out.println("Número de ID de la habitación: " + otraHabitacion.getNumero());
                            System.out.println("Categoria de la habitación: " + otraHabitacion.getCategoria());
                            System.out.println("Cedula del Paciente: " + paciente.getCedula());
                            System.out.println("Nombre del Paciente: "+ paciente.getNombre());
                            //se asigna la habitacion al paciente
                        } else
                        {//si no se cumple se imprime que no hay habitaciones disponibles
                            System.out.println("No hay habitaciones disponibles de ninguna categoría");
                        }
                    }
                }


            } else
            {//si no se cumple que la habitacionAsignada es null se imprime que ya tiene registrada una habitacion
                System.out.println("El paciente ya tiene una habitacion registrada");
            }




        } else
        {//si al momento de comprobar si la cedula del paciente se encuentra en la lista de pacientes registardos y no lo esta imprime que se devuelva y se registre
            System.out.println("El paciente no se encuentra registrado. Por favor, regístrese en el hospital para poder atenderlo.");

        }

    }

    public static void vacunacion(Hospital hospital){

        System.out.println("Ingrese la cédula del paciente: ");

        int numeroCedula;
        numeroCedula= sc.nextInt();

        //Se busca el paciente por el numero de cédula
        Paciente pacienteAsignado= hospital.buscarPaciente(numeroCedula);

        //Verificación que el paciente se encuentre en la base de datos del hospital

        if (pacienteAsignado == null){
            while(true){
                //Se da la posibilidad de registrar ese paciente nuevo
                System.out.println("El paciente no está registrado.\n¿Desea registrarlo?");
                System.out.println("1. Si\n2.No \nSeleccione una opción");
                byte opcion= sc.nextByte();
                switch (opcion){
                    case 1:
                        registrarPaciente(hospital);
                        return;

                    case 2:
                        System.out.println("Adiós");
                        return;

                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }

        //Mensaje de bienvenida
        System.out.println(pacienteAsignado.bienvenida());

        //Se pregunta por el tipo de vacuna que requiere el paciente
        byte tipoVacuna;

        System.out.println("\nSeleccione el tipo de vacuna que requiere");
        System.out.println("1. Obligatoria");
        System.out.println("2. No obligatoria");
        System.out.println("Ingrese una opcion: ");

        tipoVacuna= sc.nextByte();

        //Validación de la opción ingresada
        while ((tipoVacuna<1) || (tipoVacuna>2)){
            System.out.println("Opción fuera de rango, por favor ingrese otro número: ");
            tipoVacuna = sc.nextByte();
        }

        System.out.println("Vacunas Disponibles");

        ArrayList<Vacuna> vacunasDisponibles = new ArrayList<Vacuna>();

        //Busca las vacunas disponibles según su Eps y tipo de Vacuna
        switch (tipoVacuna){
            case 1:
                //Se busca vacunas disponibles para la EPS del paciente, y que sean obligatorias.
                vacunasDisponibles= pacienteAsignado.buscarVacunaPorEps("Obligatoria",hospital);
                //Validación por si no tiene vacunas disponibles
                if (vacunasDisponibles.size()==0){
                    System.out.println("No hay vacunas disponibles para usted de tipo obligatoria");
                    return;
                }
                //Impresión de esas vacunas disponibles
                for (int i=1; i<=vacunasDisponibles.size(); i++){
                    System.out.println(i + "."+vacunasDisponibles.get(i-1).getNombre());
                }
                break;
            case 2:
                //Se busca vacunas disponibles para la EPS del paciente, y que no sean obligatorias
                vacunasDisponibles= pacienteAsignado.buscarVacunaPorEps("No obligatoria",hospital);
                //Validación por si no tiene vacunas disponibles
                if (vacunasDisponibles.size()==0){
                    System.out.println("No hay vacunas disponibles para usted de tipo no obligatoria");
                    return;
                }
                //Impresión de esas vacunas disponibles
                for (int i=1; i<=vacunasDisponibles.size(); i++){
                    System.out.println(i + "."+vacunasDisponibles.get(i-1).getNombre());
                }
                break;
        }

        System.out.println("\nSeleccione la vacuna que requiere aplicarse: ");

        byte numeroVacuna;
        numeroVacuna = sc.nextByte();

        //Si la variable verificarVacuna es false, el paciente no se ha puesto esa vacuna anteriormente.
        boolean verificarVacuna=false;

        do{
            //Se verifica que la opción ingresada no esté fuera de rango
            while ((numeroVacuna<1) || (numeroVacuna>vacunasDisponibles.size())){
                System.out.println("Opción fuera de rango, por favor ingrese otro número: ");
                numeroVacuna = sc.nextByte();
            }
            // Se verifica que la vacuna seleccionada no se la haya puesto antes
            for (int i=1;i<=pacienteAsignado.getHistoriaClinica().getHistorialVacunas().size();i++){
                if (Objects.equals(pacienteAsignado.getHistoriaClinica().getHistorialVacunas().get(i - 1).getVacuna().getNombre(), vacunasDisponibles.get(numeroVacuna - 1).getNombre())){
                    verificarVacuna=true;
                    System.out.println("Usted ya se puso esta vacuna, por favor ingrese otra opción o ingrese el número 0 para terminar el proceso: ");
                    numeroVacuna= sc.nextByte();;
                    if (numeroVacuna==0){
                        return;
                    }
                    break;
                }else {
                    verificarVacuna=false;
                }
            }
        }while ( (verificarVacuna==true) || ((numeroVacuna<1) || (numeroVacuna>vacunasDisponibles.size())));

        ArrayList<CitaVacuna> agendaDisponible = new ArrayList<CitaVacuna>();
        System.out.println("\nCitas disponibles: ");

        //Se busca la agenda disponible de la vacuna seleccionada
        Vacuna vacunaAsignada = vacunasDisponibles.get(numeroVacuna-1);
        agendaDisponible = vacunaAsignada.mostrarAgendaDisponible();

        //Caso en el que no hayan citas disponibles de esa vacuna
        if(agendaDisponible.size()==0){
            System.out.println("No hay citas disponibles para esta vacuna");
            return;
        }

        //Se imprime las citas disponibles
        for(int i=1; i<=agendaDisponible.size();i++){
            System.out.println(i + ". "+ agendaDisponible.get(i-1).getFecha());
        }

        //Selecciona la cita
        System.out.println("\nSeleccione la cita de su preferencia: ");

        byte numeroCita;
        numeroCita= sc.nextByte();

        //Se actualiza la agenda de la vacuna (Se asigna el paciente a esa cita de vacuna)

        CitaVacuna citaAsignada = vacunaAsignada.actualizarAgenda(pacienteAsignado, numeroCita,agendaDisponible);

        System.out.println("\nCita asignada correctamente, puede acudir al centro asistencial con la siguiente informacion: ");

        //Se agrega esa vacuna al historial de vacunas del paciente de la historia clínica
        pacienteAsignado.actualizarHistorialVacunas(citaAsignada);

        System.out.println("\nResumen de su cita: ");
        System.out.println("Fecha: " + citaAsignada.getFecha());
        System.out.println("Paciente: " + citaAsignada.getPaciente().getNombre());
        System.out.println("Vacuna: "+ citaAsignada.getVacuna().getNombre());
        System.out.println("Asistente médico: Enfermera ") ;

        //Limpia los arrays de agenda de las vacunas y las vacunas disponibles.
        agendaDisponible.clear();
        vacunasDisponibles.clear();

        //Por último se muestra el historial de vacunas del paciente
        System.out.println("\nEste es el historial de vacunas aplicadas del paciente seleccionado: ");
        //pacienteAsignado.mostrarHistorialVacunas();
        for (int i=1; i<=pacienteAsignado.getHistoriaClinica().getHistorialVacunas().size();i++){
            System.out.println(i + ". Vacuna: "+pacienteAsignado.getHistoriaClinica().getHistorialVacunas().get(i-1).getVacuna().getNombre());
        }

        //Despedida
        System.out.println("\n"+pacienteAsignado.despedida(citaAsignada));
    }


    public static void facturacion(Hospital hospital) {
        // Buscar paciente
        Paciente pacienteSeleccionado;

        do {
            System.out.println("Ingrese la cedula del paciente:");
            pacienteSeleccionado = hospital.buscarPaciente(Integer.parseInt(sc.nextLine()));

            if (pacienteSeleccionado == null) {
                System.out.println("No existe un paciente registrado con esta cedula. Desea intentar de nuevo? (S/N)");
                if (sc.nextLine().equalsIgnoreCase("n")) {
                    return;
                }
            }
        } while (pacienteSeleccionado == null);

        // Buscar servicios pendientes de pago
        ArrayList<Servicio> serviciosSinPagar = Servicio.obtenerServiciosSinPagar(pacienteSeleccionado);

        if (serviciosSinPagar.size()==0){
            System.out.println("Usted no tiene ningún servicio pendiente de pago");
            return;
        }
        System.out.println("Servicios pendientes de pago:");

        for (Servicio servicio :
                serviciosSinPagar)
            System.out.println(servicio.descripcionServicio());

        // Seleccionar servicio a pagar
        Servicio servicioSeleccionado = null;
        System.out.println("Ingrese la ID del servicio que va a pagar:");
        do {
            long idSeleccionada = Long.parseLong(sc.nextLine());
            for (Servicio servicio :
                    serviciosSinPagar) {
                if (servicio.getIdServicio() == idSeleccionada){
                    servicioSeleccionado = servicio;
                    break;
                }
            }
            if (servicioSeleccionado == null) {
                System.out.println("No existe el servicio seleccionado. Intente de nuevo.");
            }
        } while (servicioSeleccionado == null);

        // Calcular precio
        double precioServicioSeleccionado = 0;
        if (servicioSeleccionado instanceof Formula)
            precioServicioSeleccionado = pacienteSeleccionado.calcularPrecio((Formula) servicioSeleccionado);
        else if (servicioSeleccionado instanceof CitaVacuna)
            precioServicioSeleccionado = pacienteSeleccionado.calcularPrecio((CitaVacuna) servicioSeleccionado);
        else if (servicioSeleccionado instanceof Habitacion)
            precioServicioSeleccionado = pacienteSeleccionado.calcularPrecio((Habitacion) servicioSeleccionado);
        else if (servicioSeleccionado instanceof Cita)
            precioServicioSeleccionado = pacienteSeleccionado.calcularPrecio((Cita) servicioSeleccionado);

        // Realizar pago
        System.out.println("Total a pagar: $" + precioServicioSeleccionado);
        System.out.println("Realizar pago? (S/N)");

        if (sc.nextLine().equalsIgnoreCase("s")) {
            servicioSeleccionado.validarPago(pacienteSeleccionado, servicioSeleccionado.getIdServicio());
            System.out.println("Pago realizado");
        }
        else {
            System.out.println("Pago cancelado");
        }

        // Limpiar la lista de servicios sin pagar
        serviciosSinPagar.clear();
    }


///////////////////////////////// GESTION ///////////////////////////////////////////////

///------------------------------- gestion doctores------------------------------------///


	public static void menuGestionDoctor(Hospital hospital) {
	byte opcion;
	do {
		// Menu para gestionar la clase Doctor
		System.out.println("\nMENU Gestion Doctor");
		System.out.println("1. Registrar doctor");
		System.out.println("2. Eliminar doctor");
		System.out.println("3. Ver doctor");
		System.out.println("4. Agregar citas");
		System.out.println("5. Eliminar citas");
		System.out.println("6. --Regresar al menu anterior--");
		System.out.println("7. --Salir--");
		System.out.println("Ingrese una opcion: ");
		opcion = sc.nextByte();
		switch (opcion) {
			case 1 -> registrarDoctor(hospital);
			case 2 -> eliminarDoctor(hospital);
			case 3 -> verDoctor(hospital);
			case 4 -> agregarCitas(hospital);
			case 5 -> eliminarCitas(hospital);
			case 6 -> {
				return;
			}
			case 7 -> {
				Serializador.serializar(hospital);
				System.exit(0);
			}
		}
	} while (true);
}


	public static void registrarDoctor (Hospital hospital){

	Scanner sc= new Scanner(System.in);

	System.out.println("Por favor introduce la información del doctor para su registro");
	System.out.println("Ingrese el nombre del doctor:");
	String nombre = sc.next();
	System.out.println("Ingrese el número de cédula: ");
	int id = sc.nextInt();

	if (hospital.buscarDoctor(id) != null) {
		System.out.println("Este doctor ya esta registrado");
		return;
	}

	System.out.println("Ingrese su tipo de EPS 'Subsidiado','Contributivo' o 'Particular':");
	String eps = sc.next();
	System.out.println("Ingrese su especialidad 'General', 'Odontologia' o 'Oftalmologia': ");
	String especialidad = sc.next();

	Doctor doctor = new Doctor(id, nombre, eps, especialidad);
	System.out.println("¡El doctor ha sido registrado con éxito!");
	hospital.getListaDoctores().add(doctor);
	System.out.println(doctor);
}

	public static void eliminarDoctor(Hospital hospital) {
	Scanner sc= new Scanner(System.in);
	System.out.println("Ingrese la cédula del doctor que se eliminará: ");
	int cedula = sc.nextInt();
	Doctor doctor = hospital.buscarDoctor(cedula);
	if (doctor == null) { /*Si el doctor es null, quiere decir que no lo encontró, por lo que
	preguntamos si desea registrar este doctor */
		while (true) {
			System.out.println("El doctor no esta registrado.\n¿Desea registrarlo?");
			System.out.println("1. Si\n2. No \nSeleccione una opción");
			byte opcion = sc.nextByte();
			switch (opcion) {
				case 1:
					registrarDoctor(hospital);
					return;

				case 2:
					System.out.println("Adios");
					return;
				default:
					System.out.println("Opción Inválida");
			}
		}
	}
	hospital.getListaDoctores().remove(doctor);
	System.out.println("¡Doctor eliminado!");
}




}
