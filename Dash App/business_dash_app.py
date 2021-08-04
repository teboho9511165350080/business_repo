# -*- coding: utf-8 -*-
"""
Created on Tue Jun 15 18:02:52 2021

@author: teboho lekeno
"""
import dash
import base64
import dash_core_components as dcc
import dash_html_components as html
import dash_bootstrap_components as dbc
import plotly.express as px
from dash.dependencies import Input, Output

import online_db_handler as ohs

handler = ohs.Online_DB_Handler()
data = handler.read_database("debitorsrecordonline")

app = dash.Dash(__name__, external_stylesheets=[dbc.themes.BOOTSTRAP],
                suppress_callback_exceptions=True)

logo_card = html.Div(dbc.CardImg())

substations_card = dbc.Card([
    dbc.CardHeader("Power Stations"),
    dbc.CardBody(
        [
            dcc.Dropdown(
                id = 'power-generators',
                options = [
                    {'label': content, 'value': content}
                    for content in data[1].unique()
                ],
                value = "", multi = True,
           )
        ] 
    )
])

radio_card = dbc.Card([
    dbc.CardHeader("Select Slider Type"),
    dbc.CardBody(
        [
            dcc.RadioItems(
                id = 'slider-type',
                options = [
                    {'label':'Single Slider', 'value': 'slider'},
                    {'label':'Range Slider', 'value': 'range_slider'}
                ], 
                value = 'range_slider',
                inputStyle={"margin-right": "20px"},
                labelStyle = {'margin-right':'40px'}
            )
        ]
    )
])

generic_slider_card = dbc.Card([
    dbc.CardHeader("Study Year"),
    dbc.CardBody(
        [
            radio_card, html.Br(),
            html.Div(id = 'generic-slider-container', children=[])
        ]
    )
])

map_card = dbc.Card(
    dcc.Graph(id='graph', figure = {}, config={
        'displayModeBar': False, 'scrollZoom': True},style={
            'height':'93vh'})
) 
            
generator_card = dbc.Card(
    dcc.Graph(id='graph-generator', figure = {}, config={
        'displayModeBar': False, 'scrollZoom': True},style={
            'height':'30vh'})
) 
            
line_card = dbc.Card(
    dcc.Graph(id='graph-line', figure = {}, config={
        'displayModeBar': False, 'scrollZoom': True},style={
            'height':'30vh'})
) 
            
transformer_card = dbc.Card(
    dcc.Graph(id='graph-transformer', figure = {}, config={
        'displayModeBar': False, 'scrollZoom': True},style={
            'height':'30vh'})
) 
            
            
tab_holder = dbc.Tabs([
    dbc.Tab(label="Map", tab_id="map"),
    dbc.Tab(label="Graphs", tab_id="graphs"),
    ],
    id="tabs",
    active_tab="map",
)

app.layout = dbc.Container([
    
    dbc.Row([
        dbc.Col(
            [
                logo_card, html.Br(), substations_card, html.Br(),
                generic_slider_card,   
            ], width = 4
        ),
            
        dbc.Col([tab_holder, html.Div(id="tab-content")], width = 8)], justify = 'around')
    
], fluid = True,  style={"height": "100vh"})

@app.callback(
    Output("tab-content", "children"),
    Input("tabs", "active_tab"))

def render_tab_content(active_tab):

    if active_tab:
        if active_tab == "map":
            return map_card
        else:
            return generator_card, line_card, transformer_card

#Output of Generic Slider
@app.callback(Output('generic-slider-container', 'children'),
              Input('slider-type', 'value'))

def update_generic_slider(chosen_slider):
    if chosen_slider == 'range_slider':
        slid = html.Div(
            children = [
                dcc.RangeSlider(
                    id = 'study-years',
                    min = data[6].min(),
                    max = data[6].max(),
                    value = [data[6].min(),data[6].min()+1],
                    dots = True,
                    allowCross = False,
                    disabled = False,
                    pushable = 1,
                    updatemode = 'mouseup',
                    marks = {i: str(i) for i in range(data[6].max()+1)}
                )
            ]
        )
        return slid
    
    elif chosen_slider == 'slider':
        slid = html.Div(
            children = [
                dcc.Slider(
                    id = 'study-years',
                    min = data[6].min(),
                    max = data[6].max(),
                    value = [data[6].min()],
                    marks = {i: str(i) for i in range(data[6].max()+1)},
                    included = False
                )
            ]
         ) 
        return slid




if __name__ == '__main__':
    app.run_server(debug=False)