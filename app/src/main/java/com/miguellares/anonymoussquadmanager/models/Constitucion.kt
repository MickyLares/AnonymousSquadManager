package com.miguellares.anonymoussquadmanager.models




sealed class Constitucion(
    val constitucion: List<BodyConstitucion>
) {
    object AnonymousConstitution : Constitucion(
        listOf(
            BodyConstitucion.capitulo_1,
            BodyConstitucion.capitulo_2,
            BodyConstitucion.capitulo_3,
            BodyConstitucion.capitulo_4,
            BodyConstitucion.capitulo_5,
            BodyConstitucion.Anexo1,
            BodyConstitucion.Anexo2
        )
    )
}

sealed class BodyConstitucion(
    val titulo: String,
    val capitulos: List<Articulos>
) {
    object capitulo_1 : BodyConstitucion(
        "Capitulo 1. PERTENENCIA AL ESCUADRÓN",
        capitulos = listOf(
            Articulos.articulos1,
            Articulos.articulos2,
            Articulos.aeticulo2bis
        )
    )

    object capitulo_2 : BodyConstitucion(
        titulo = "Capitulo 2. PARTIDAS DEL ESCUADRÓN",
        capitulos = listOf(
            Articulos.Articulo3,
            Articulos.Articulo3bis,
            Articulos.Articulo3bis1,
            Articulos.Articulo3bis2,
            Articulos.Articulo3bis3,
            Articulos.Articulo4
        )
    )

    object capitulo_3 : BodyConstitucion(
        "Capitulo 3. SIGNIFICADO DEL ESCUADRÓN",
        capitulos = listOf(
            Articulos.Articulo5,
            Articulos.Articulo6
        )
    )

    object capitulo_4 : BodyConstitucion(
        "Capitulo 4. CARÁCTER DEL ESCUADRÓN",
        capitulos = listOf(
            Articulos.Articulo7,
            Articulos.Articulo7bis,
            Articulos.Articulo8,
            Articulos.Articulo8bis,
            Articulos.Articulo9,
            Articulos.Articulo10
        )
    )

    object capitulo_5 : BodyConstitucion(
        "Capitulo 5. COMITÉ DE ADMINISTRACIÓN",
        capitulos = listOf(
            Articulos.Articulo11,
            Articulos.Articulo12,
            Articulos.Articulo12bis
        )
    )

    object Anexo1 : BodyConstitucion(
        "Anexo 1: TABLA DE PONTENCIA MÁXIMA PARA ANONYMOUS",
        capitulos = listOf(
            Articulos.TablaPotencia
        )
    )
    object Anexo2: BodyConstitucion(
        "OFICIALIDAD Y UNIFICACIÓN DE COLORES",
        capitulos = listOf(
            Articulos.Oficialidad
        )
    )
}

sealed class Articulos(
    val numero: String,
    val contenido: String
) {
    object articulos1 : Articulos(
        "Art 1:",
        contenido = "Formará parte como miembro de legítimo derecho cualquier persona que no forme parte de otro equipo y cuente con la aprobación del Comité de Administradores bajo SUFRAGIO CONSTITUTIVO. Previamente la persona propuesta para formar parte, debe aceptar la normativa que se encuentre vigente. Y una vez aprobado en sufragio se le entregará un parche identificativo."
    )

    object articulos2 : Articulos(
        "Art 2:",
        contenido = "El ESCUADRÓN está formado por 31 personas. No se puede incluir un miembro si no hay una vacante libre. Los nuevos integrantes deben ser propuestos por otro miembro del ESCUADRÓN comunicando su intención a los Administradores."
    )

    object aeticulo2bis : Articulos(
        "Art 2.1:",
        "En el caso de menores de edad solo pueden ser propuestos por alguno de sus tutores legales. La asistencia del menor a las partidas queda supeditada a la normativa de cada campo."
    )

    object Articulo3 : Articulos(
        "Art 3:",
        "Se define como PARTIDA OFICIAL DEL ESCUADRON a la propuesta por cualquier operador por cualquiera de los grupos de comunicación del ESCUADRÓN y a la que asisten como mínimo 5 miembros del equipo. Del mismo modo serán OFICIALES los entrenamientos y Milsims convocadas por la misma vía."
    )

    object Articulo3bis : Articulos(
        "Art 3.1:",
        "Cuando se asista a una partida se designará un jefe de EQUIPO o TEAM LEADER para eseevento. El cual informará a Administración de la asistencia de todos los miembros para poder registrarla de modo oficial."
    )

    object Articulo3bis1 : Articulos(
        "Art 3.2:",
        "Las partidas a las que asistan cualquier miembro del ESCUADRÓN deben hacerse públicas para el resto. Se trata de dar a conocer, promover e invitar cortésmente al resto de compañeros."
    )

    object Articulo3bis2 : Articulos(
        "Art 3.3:",
        "Del mismo modo que en el articulo anterior, si se cuenta con disponibilidad de asistir a una partida en la misma fecha que la convicada, se debe asistir a la PARTIDA OFICIAL DEL EQUIPO. Teniendo en cuenta que no son iguales las Milsims y su particularidad de convocatoria, económicas y participativas. Si que pueden coincidir en fecha una MILSIM y una PARTIDA OFICIAL."
    )

    object Articulo3bis3 : Articulos(
        "Art 3.4:",
        "En el caso de una convocatoria de PARTIDA OFICIAL con otro equipo, y dadas las dificultades organizativas, será el TEAM LEADER o la Administración quien se organice y comunique con el otro equipo."
    )

    object Articulo4 : Articulos(
        "Art 4:",
        "Para seguir formando parte del ESCUADRÓN ANONYMOUS, se precisa la asistencia de al menos 4 partidas en un periodo de 12 meses. Los propuestos para ingresar deben acumular esas partidas antes de comunicarle la propuesta a Administración."
    )

    object Articulo5 : Articulos(
        "Art 5:",
        "El ESCUADRÓN ANONYMOUS es un equipo de Airsoft que práctica un modo de juego basado en la simulación táctica militar"
    )

    object Articulo6 : Articulos(
        "Art 6:",
        "Con el fin de unificarse, uniformarse, camuflarse y de intercambiar partidas con otros equipos oficiales, se cuenta con uniformidad oficial que se designará por parte de Administración y contará con carácter CONSTITUTIVO Y SUJETA A SUFRAGIO."
    )

    object Articulo7 : Articulos(
        "Art 7:",
        "El ESCUADRON posee un carácter integrador y respetuoso con cualquier condición física, mental, religiosa, procedencia, sexo, raza, políticas ect...  No tiene más importancia el pasado que el futuro como miembros del ESCUADRON. Por tanto el Escuadrón se caracteriza por su trato educado, conciliador, y respetuoso entre sus miembros, en las partidas y para con sus adversarios."
    )

    object Articulo7bis : Articulos(
        "Art 7.1:",
        "En los Grupos, Redes Sociales y sobre los uniformes oficiales se prohíbe la pornografía, simbología, y expresiones que puedan ofender a otros miembros del ESCUADRON."

    )

    object Articulo8 : Articulos(
        "Art 8:",
        "Las decisiones del ESCUADRON poseen un carácter democrático, intentando someter a votación cada una de sus decisiones."
    )

    object Articulo8bis : Articulos(
        "Art 8.1:",
        "Los SUFRAGIOS que sean destinados a algún cambio CONSTITUTIVO, sobre esta normativa deben ser votados en el GRUPO GENERAL, a propuesta de Administración, y debe ser aprobada por al menos 4/5 partes de los convocados a votar. No se admiten abstenciones en este tipo de sufragios.  En el resto de votaciones se tomará la decisión más votada."
    )

    object Articulo9 : Articulos(
        "Art 9:",
        "Se exige a todos los OPERADORES DE ANONYMOUS el compromiso de jugar con el HONOR que se exige en este juego en su grado mas alto. Aceptamos las normas de cada campo y partidas a las que asistimos. Igualmente asumimos unos valores máximos de potencias y gramajes de BB, los cuales nunca deben ser sobrepasados. Por supuesto si un campo o partida nos pide rebajar esos valores, los debemos adaptar a la normativa que se nos trasmita. Si un campo cuenta con valores superiores los Anonymous no sobrepasaremos nuestra propia tabla de potencias."
    )

    object Articulo10 : Articulos(
        "Art 10:",
        "Cualquier falta de disciplina para con cualquiera de la normativa CONSTITUCIONAL DEL ESCUADRON ANONYMOUS, será tomada como una falta de respeto al resto de operadores. Por lo que conlleva una PROPUESTA DE EXPULSION por parte del COMITÉ Administrativo a resolver en 7 días naturales."
    )

    object Articulo11 : Articulos(
        "Art 11:",
        "Dado las dificultades que conlleva organizar, administrar, unificar, etc. a un grupo tan extenso y variado. Y que a la vez sea todo sometido a votación hace muy difícil llegar a acuerdos que nos permitan avanzar en algunos aspectos importantes. Para ello se forma un COMITÉ DE ADMINISTRACION donde todas las propuestas puedan tomar la forma mas conveniente par todos."
    )

    object Articulo12 : Articulos(
        "Art 12.1:",
        "El COMITÉ estará formado por 7 miembros del ESCUADRON, que sean mayores de edad y tengan al menos 1 año de antigüedad como miembro de pleno derecho."
    )

    object Articulo12bis : Articulos(
        "Art 12.2:",
        " La formación de cada COMITÉ se hará por votación en el Grupo General y tendrá una duración máxima de 24 meses."
    )

    object TablaPotencia : Articulos(
        "Los siguientes valores no son CONSTITUTIVOS, pueden ser cambiados por sufragio y mayoría SIMPLE:",
        "PISTOLA: 0.28gr. y 1 Julio de potencia, sin distancia seguridad.\n" +
                "PRIMARIA  PARA 0 METROS: 0.28gr. y 1 Julio de potencia, sin distancia seguridad.\n" +
                "FUSIL: 0.36gr. y 1.14 julios de potencia, 5 metros de distancia de seguridad.\n" +
                "APOYO: 0.36GR. Y 1.49 julios de potencia, 10 metros de seguridad.\n" +
                "SELECTO: 0.40gr. y 1.88 julios de potencia, 15 metros de seguridad.\n" +
                "SNIPER: 0.45gr. y 2.81 julios de potencia, 20 metros de seguridad.\n" +
                "El crono debe pasarse con el HOP-UP graduado para el juego (tiro tenso), y con el gramaje y la bolas del operador. \n" +
                "La réplica que no se encuentre en potencia no podrá usarse en el juego."
    )

    object Oficialidad : Articulos(
        "",
        "Para dar cumplimiento al Art 6 de la CONSTITUCION ANONYMOUS, y con el fin y la necesidad de distinguirnos en el terreno de juego ya sean en partidas PRIVADAS, PUBLICAS Y CONTRA OTROS EQUIPOS, se tiene que hacer oficial una serie de colores identificativos. Las partidas CONTRA OTROS EQUIPOS deben adoptarse con los colores oficiales que determine el TL. En las partidas PUBLICAS Y PRIVADAS DEL ESCUADRON  debe aplicarse la normativa que exija el TL  y de exigirse por parte del TL una obligación de colores debe ceñirse a lo que es oficial, si el TL llega a un acuerdo unánime con todos los operadores pueden llevar la equipación que el TL proponga o determine, la que sea indistintamente que sea uniforme o no.  Cualquier miembro de Anonymous puede asistir a las partidas NO oficiales como guste. Por el contrario si la Partida es OFICIAL, y el TL lo exige, el que no se presente con estos colores no puede jugar OFICIALMENTE: \n" +
                "A partir del 1º de Enero de 2024 los colores oficiales consisten en: \n" +
                "PRIMERA EQUIPACION \n" +
                "•\tCAMISOLA O COMBAT de patrón MULTICAM \n" +
                "•\tPANTALON cualquiera de color MARRON, TAN O COYOTE\n" +
                "\n" +
                "\n" +
                "A partir del 1º de Enero de 2025 se adaptan los colores oficiales:\n" +
                "SEGUNDA EQUIPACION \n" +
                "•\tCAMISOLA O COMBAT de patrón FLECKTARN ALEMAN.\n" +
                "•\tPANTALON cualquiera de color MARRON, TAN O COYOTE.\n" +
                "\n" +
                "El rol de Sniper puede llevar los colores que mejor determine\n"
    )
}



